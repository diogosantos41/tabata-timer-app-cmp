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
                        selectedWorkTime = it.selectedWorkTime.copy(seconds = action.preset.workTime),
                        selectedRestTime = it.selectedRestTime.copy(seconds = action.preset.restTime),
                        selectedRounds = action.preset.rounds
                    )
                }
            }

            is WorkoutSetupAction.OnWorkTimeChanged -> {
                _state.update {
                    it.copy(
                        selectedWorkTime = it.selectedWorkTime.copy(
                            seconds = (it.selectedWorkTime.seconds + action.change.delta)
                                .coerceAtLeast(1)
                        )
                    )
                }
            }

            is WorkoutSetupAction.OnRestTimeChanged -> {
                _state.update {
                    it.copy(
                        selectedRestTime = it.selectedRestTime.copy(
                            seconds = (it.selectedRestTime.seconds + action.change.delta)
                                .coerceAtLeast(1)
                        )
                    )
                }
            }

            is WorkoutSetupAction.OnRoundsChanged -> {
                _state.update {
                    it.copy(
                        selectedRounds = (it.selectedRounds + action.change.delta)
                            .coerceAtLeast(1)
                    )
                }
            }

            WorkoutSetupAction.OnStartWorkoutClick -> {
                viewModelScope.launch {
                    eventChannel.send(
                        WorkoutSetupEvent.OnStartWorkout(
                            workTime = state.value.selectedWorkTime.seconds,
                            restTime = state.value.selectedRestTime.seconds,
                            rounds = state.value.selectedRounds
                        )
                    )
                }
            }
        }
    }

    private fun initializeState() {
        val preset = defaultPreset
        _state.update {
            it.copy(
                selectedWorkTime = TimeUi(
                    seconds = preset.workTime,
                ),
                selectedRestTime = TimeUi(
                    seconds = preset.restTime,
                ),
                selectedRounds = preset.rounds,
            )
        }
    }
}