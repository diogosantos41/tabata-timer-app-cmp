package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.workout.presentation.WorkoutSessionCoordinator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WorkoutSessionViewModel(
    private val sessionCoordinator: WorkoutSessionCoordinator
) : ViewModel() {

    private var hasLoadedInitialData = false

    private var workoutSessionItems: List<WorkoutSessionItem> = emptyList()

    private val _state = MutableStateFlow(WorkoutSessionState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                setupSession()
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
            WorkoutSessionAction.OnResumePauseClick -> {}
            WorkoutSessionAction.OnSkipExerciseClick -> {}
            WorkoutSessionAction.OnStopWorkoutClick -> {}
        }
    }

    private fun setupSession() {
        workoutSessionItems = sessionCoordinator.getSessionItems() ?: emptyList()
        workoutSessionItems?.let { items ->
            val initialSessionItem = items[0]
            val nextWorkoutDescription = items[1]
            val rounds = items[workoutSessionItems.lastIndex].round
            _state.update {
                it.copy(
                    currentRound = initialSessionItem.round,
                    rounds = rounds,
                    currentWorkoutDescription = initialSessionItem.description,
                    currentWorkoutType = initialSessionItem.workoutType,
                    nextWorkoutDescription = nextWorkoutDescription.description,
                    roundSecondsRemaining = workoutSessionItems[0].seconds,
                )
            }
        }
    }

    private fun onWorkoutFinished() {
        sessionCoordinator.clearSession()
        // event.send()
    }
}

