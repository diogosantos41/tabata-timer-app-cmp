package com.dscoding.tabatatimer.workout.presentation.workout_finished

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.workout.presentation.util.WorkoutSessionStore
import com.dscoding.tabatatimer.workout.presentation.workout_finished.utils.getRandomWorkoutCompletionMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutFinishedViewModel(
    private val sessionStore: WorkoutSessionStore,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<WorkoutFinishedEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(WorkoutFinishedState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                setCompletionMessage()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorkoutFinishedState()
        )

    fun onAction(action: WorkoutFinishedAction) {
        when (action) {
            WorkoutFinishedAction.OnGoBackToWorkoutSetup -> {
                sessionStore.clearSession()
                viewModelScope.launch {
                    eventChannel.send(WorkoutFinishedEvent.GoBackToWorkoutSetup)
                }
            }

            else -> Unit
        }
    }

    private fun setCompletionMessage() {
        _state.update {
            it.copy(
                completionMessage = getRandomWorkoutCompletionMessage()
            )
        }
    }
}