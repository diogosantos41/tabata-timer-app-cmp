package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.core.domain.WorkoutPreferences
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.domain.WorkoutSessionCoordinator
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
    private val workoutPreferences: WorkoutPreferences,
    private val workoutSessionCoordinator: WorkoutSessionCoordinator
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
                observeSoundEnabled()
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
                        workoutSessionCoordinator.pauseTimer()
                        _state.update {
                            it.copy(currentTimerPlayState = TimerPlayState.Paused)
                        }
                    }

                    TimerPlayState.Paused -> {
                        workoutSessionCoordinator.resumeTimer()
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
                workoutSessionCoordinator.stopTimer()
                sessionStore.clearSession()
                viewModelScope.launch {
                    eventChannel.send(WorkoutSessionEvent.StopWorkout)
                }
            }

            WorkoutSessionAction.OnToggleSoundClick -> {
                viewModelScope.launch {
                    workoutPreferences.setSoundEnabled(!state.value.isSoundEnabled)
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

        workoutSessionCoordinator.runCountdownTimer(
            scope = viewModelScope,
            totalSeconds = currentItem.seconds,
            onComplete = { onCountdownCompleted() },
            onTimeTick = { millis ->
                _state.update {
                    it.copy(
                        roundMillisRemaining = millis,
                    )
                }
            }
        )
    }

    private fun onCountdownCompleted() {
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
        workoutSessionCoordinator.stopTimer()
        viewModelScope.launch {
            eventChannel.send(WorkoutSessionEvent.SessionCompleted)
        }
    }

    private fun observeSoundEnabled() {
        workoutPreferences
            .observeSoundEnabled()
            .onEach { soundEnabled ->
                _state.update {
                    it.copy(
                        isSoundEnabled = soundEnabled
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

