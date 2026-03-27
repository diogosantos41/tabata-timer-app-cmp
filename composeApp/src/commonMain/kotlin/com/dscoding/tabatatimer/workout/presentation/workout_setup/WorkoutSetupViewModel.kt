package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.tabatatimer.core.presentation.utils.toTimeFormat
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.defaultPreset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WorkoutSetupViewModel : ViewModel() {

    private var hasLoadedInitialData = false

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
            is WorkoutSetupAction.OnPresetClick -> {}
            WorkoutSetupAction.OnRestTimeChanged -> {}
            WorkoutSetupAction.OnRoundsChanged -> {}
            WorkoutSetupAction.OnStartWorkoutClick -> {}
            WorkoutSetupAction.OnWorkTimeChanged -> {}
        }
    }

    private fun initializeState() {
        val preset = defaultPreset
        val totalWorkSeconds = preset.work * preset.rounds
        val totalRestSeconds = preset.rest * preset.rounds
        val totalSeconds = totalWorkSeconds + totalRestSeconds
        _state.update {
            it.copy(
                selectedWorkTime = TimeUi(
                    seconds = preset.work,
                    weight = 0.66f
                ),
                selectedRestTime = TimeUi(
                    seconds = preset.rest,
                    weight = 0.33f
                ),
                selectedRounds = preset.rounds,
                totalWorkoutTime = totalWorkSeconds.toTimeFormat(),
                totalRestTime = totalRestSeconds.toTimeFormat(),
                totalWorkTime = totalSeconds.toTimeFormat()
            )
        }
    }
}