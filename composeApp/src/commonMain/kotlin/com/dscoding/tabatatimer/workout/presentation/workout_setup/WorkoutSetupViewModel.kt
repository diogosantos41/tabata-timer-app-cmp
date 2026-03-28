package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.defaultPreset
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutSetupViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<WorkoutSetupEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(WorkoutSetupState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                initializeState()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorkoutSetupState()
        )


    fun onAction(action: WorkoutSetupAction) {
        when (action) {
            is WorkoutSetupAction.OnPresetClick -> {
                _state.update {
                    it.copy(
                        selectedWorkTime = it.selectedWorkTime?.copy(seconds = action.preset.work),
                        selectedRestTime = it.selectedRestTime?.copy(seconds = action.preset.rest),
                        selectedRounds = action.preset.rounds
                    )
                }
            }

            WorkoutSetupAction.OnRestTimeChanged -> {}
            WorkoutSetupAction.OnRoundsChanged -> {}
            WorkoutSetupAction.OnStartWorkoutClick -> {
                viewModelScope.launch {
                    eventChannel.send(
                        WorkoutSetupEvent.OnStartWorkout(
                            work = state.value.selectedWorkTime?.seconds ?: 0,
                            rest = state.value.selectedRestTime?.seconds ?: 0,
                            rounds = state.value.selectedRounds ?: 0
                        )
                    )
                }
            }

            WorkoutSetupAction.OnWorkTimeChanged -> {}
        }
    }

    private fun initializeState() {
        val preset = defaultPreset
        _state.update {
            it.copy(
                selectedWorkTime = TimeUi(
                    seconds = preset.work,
                ),
                selectedRestTime = TimeUi(
                    seconds = preset.rest,
                ),
                selectedRounds = preset.rounds,
            )
        }
    }
}