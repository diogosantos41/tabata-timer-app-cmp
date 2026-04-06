package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import com.dscoding.tabatatimer.workout.domain.audio.models.SoundEffect
import com.dscoding.tabatatimer.workout.domain.timer.CountdownTimer
import com.dscoding.tabatatimer.workout.presentation.util.WorkoutSessionStore
import com.dscoding.tabatatimer.workout.presentation.workout_session.models.TimerPlayState
import com.dscoding.tabatatimer.workout.presentation.workout_session.utils.secondsToMillis
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.finish

class WorkoutSessionViewModel(
    private val sessionStore: WorkoutSessionStore,
    private val countdownTimer: CountdownTimer,
    private val workoutAudio: WorkoutAudio
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var workoutSessionItems: List<WorkoutSessionItem> = emptyList()
    private var currentSessionIndex = 0

    private val eventChannel = Channel<WorkoutSessionEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(WorkoutSessionState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                setupSession()
                observeCountdown()
                startCurrentCountdown()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorkoutSessionState()
        )

    fun onAction(action: WorkoutSessionAction) {
        when (action) {
            WorkoutSessionAction.OnResumePauseClick -> {
                when (state.value.currentTimerPlayState) {
                    TimerPlayState.Running -> {
                        countdownTimer.pause()
                        _state.update {
                            it.copy(currentTimerPlayState = TimerPlayState.Paused)
                        }
                    }

                    TimerPlayState.Paused -> {
                        countdownTimer.resume()
                        _state.update {
                            it.copy(currentTimerPlayState = TimerPlayState.Running)
                        }
                    }
                }
            }

            WorkoutSessionAction.OnSkipExerciseClick -> {
                moveToNextExerciseOrFinish()
            }

            WorkoutSessionAction.OnStopWorkoutClick -> {
                countdownTimer.stop()
                sessionStore.clearSession()
                viewModelScope.launch {
                    eventChannel.send(WorkoutSessionEvent.StopWorkout)
                }
            }
        }
    }

    private fun setupSession() {
        workoutSessionItems = sessionStore.getWorkoutSession()?.items.orEmpty()
        currentSessionIndex = 0
        updateCurrentSessionState()
    }

    private fun updateCurrentSessionState() {
        val currentItem = workoutSessionItems.getOrNull(currentSessionIndex) ?: return
        val nextItem = workoutSessionItems.getOrNull(currentSessionIndex + 1)
        val rounds = workoutSessionItems.lastOrNull()?.round ?: 1

        _state.update {
            it.copy(
                rounds = rounds,
                currentWorkoutSessionItem = currentItem,
                nextWorkoutDescription = nextItem?.description
                    ?: UiText.Resource(Res.string.finish),
                roundMillisRemaining = currentItem.seconds.secondsToMillis(),
                currentTimerPlayState = TimerPlayState.Running
            )
        }
    }

    private fun startCurrentCountdown() {
        val currentItem = workoutSessionItems.getOrNull(currentSessionIndex) ?: return

        countdownTimer.start(
            totalSeconds = currentItem.seconds,
            onComplete = { onCountdownCompleted() }
        )
    }

    private fun observeCountdown() {
        countdownTimer
            .remainingMillis
            .onEach { millis ->
                _state.update {
                    it.copy(
                        roundMillisRemaining = millis ?: 0L,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onCountdownCompleted() {
        workoutAudio.playSoundEffect(SoundEffect.WHISTLE)
        moveToNextExerciseOrFinish()
    }

    private fun moveToNextExerciseOrFinish() {
        val hasNextExercise = currentSessionIndex < workoutSessionItems.lastIndex

        if (!hasNextExercise) {
            finishWorkout()
            return
        }

        currentSessionIndex++
        updateCurrentSessionState()
        startCurrentCountdown()
    }

    private fun finishWorkout() {
        countdownTimer.stop()
        viewModelScope.launch {
            eventChannel.send(WorkoutSessionEvent.SessionCompleted)
        }
    }
}

