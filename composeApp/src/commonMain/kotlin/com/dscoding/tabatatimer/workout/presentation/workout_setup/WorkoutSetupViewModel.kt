package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.core.domain.WorkoutPreferences
import com.dscoding.tabatatimer.workout.presentation.util.WorkoutSessionFactory
import com.dscoding.tabatatimer.workout.presentation.util.WorkoutSessionStore
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.defaultPreset
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

class WorkoutSetupViewModel(
    private val sessionBuilder: WorkoutSessionFactory,
    private val sessionStore: WorkoutSessionStore,
    private val workoutPreferences: WorkoutPreferences
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<WorkoutSetupEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(WorkoutSetupState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeWorkoutSettings()
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
                        selectedWorkTime = it.selectedWorkTime.copy(seconds = action.preset.workSeconds),
                        selectedRestTime = it.selectedRestTime.copy(seconds = action.preset.restSeconds),
                        selectedRounds = action.preset.rounds
                    )
                }
            }

            is WorkoutSetupAction.OnWorkSecondsChanged -> {
                _state.update {
                    it.copy(
                        selectedWorkTime = it.selectedWorkTime.copy(
                            seconds = (it.selectedWorkTime.seconds + action.change.delta)
                                .coerceAtLeast(1)
                        )
                    )
                }
            }

            is WorkoutSetupAction.OnRestSecondsChanged -> {
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
                val workSeconds = state.value.selectedWorkTime.seconds
                val restSeconds = state.value.selectedRestTime.seconds
                val rounds = state.value.selectedRounds

                updateWorkoutSettings(
                    workSeconds = workSeconds,
                    restSeconds = restSeconds,
                    rounds = rounds
                )

                val workoutSession = sessionBuilder.buildWorkoutSession(
                    workSeconds = workSeconds,
                    restSeconds = restSeconds,
                    rounds = rounds
                )
                sessionStore.setWorkoutSession(workoutSession)

                viewModelScope.launch {
                    eventChannel.send(
                        WorkoutSetupEvent.OnStartWorkout
                    )
                }
            }
        }
    }

    private fun observeWorkoutSettings() {
        workoutPreferences.observeLastWorkoutSettings()
            .onEach { (workSeconds, restSeconds, rounds) ->
                _state.update {
                    it.copy(
                        selectedWorkTime = TimeUi(
                            seconds = workSeconds ?: defaultPreset.workSeconds,
                        ),
                        selectedRestTime = TimeUi(
                            seconds = restSeconds ?: defaultPreset.restSeconds,
                        ),
                        selectedRounds = rounds ?: defaultPreset.rounds,
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun updateWorkoutSettings(
        workSeconds: Int,
        restSeconds: Int,
        rounds: Int
    ) {
        viewModelScope.launch {
            workoutPreferences.setLastWorkoutSettings(
                workSeconds = workSeconds,
                restSeconds = restSeconds,
                rounds = rounds
            )
        }
    }
}