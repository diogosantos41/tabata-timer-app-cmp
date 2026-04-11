package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.core.domain.WorkoutPreferences
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.workout.domain.WorkoutSessionCoordinator
import com.dscoding.tabatatimer.workout.domain.session.SessionStep
import com.dscoding.tabatatimer.workout.presentation.util.WorkoutSessionStore
import com.dscoding.tabatatimer.workout.presentation.workout_session.mappers.toWorkoutSessionState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutSessionViewModel(
    private val sessionStore: WorkoutSessionStore,
    private val workoutPreferences: WorkoutPreferences,
    private val workoutSessionCoordinator: WorkoutSessionCoordinator,
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var sessionItems: List<WorkoutSessionItem> = emptyList()

    private val eventChannel = Channel<WorkoutSessionEvent>()
    val events = eventChannel.receiveAsFlow()

    val state = combine(
        workoutSessionCoordinator.engineState,
        workoutPreferences.observeSoundEnabled(),
    ) { engine, soundEnabled ->
        engine.toWorkoutSessionState(sessionItems, soundEnabled)
    }
        .onStart {
            if (!hasLoadedInitialData) {
                sessionItems = sessionStore.getWorkoutSession()?.items.orEmpty()
                startSession(sessionItems)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorkoutSessionState(),
        )

    private fun startSession(items: List<WorkoutSessionItem>) {
        if (items.isNotEmpty()) {
            workoutSessionCoordinator.start(
                sessionScope = viewModelScope,
                steps = items.map {
                    SessionStep(
                        seconds = it.seconds,
                        round = it.round
                    )
                },
                onSessionCompleted = {
                    viewModelScope.launch {
                        eventChannel.send(WorkoutSessionEvent.SessionCompleted)
                    }
                },
            )
        }
    }

    fun onAction(action: WorkoutSessionAction) {
        when (action) {
            WorkoutSessionAction.OnResumePauseClick -> workoutSessionCoordinator.togglePauseResume()

            WorkoutSessionAction.OnSkipExerciseClick -> workoutSessionCoordinator.skipCurrentStep()

            WorkoutSessionAction.OnStopWorkoutClick -> {
                workoutSessionCoordinator.release()
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

    override fun onCleared() {
        workoutSessionCoordinator.release()
        super.onCleared()
    }
}
