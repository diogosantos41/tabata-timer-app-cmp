package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.SettingChange
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.WorkoutPreset

sealed interface WorkoutSetupAction {
    data class OnPresetClick(val preset: WorkoutPreset) : WorkoutSetupAction
    data class OnWorkTimeChanged(val change: SettingChange) : WorkoutSetupAction
    data class OnRestTimeChanged(val change: SettingChange) : WorkoutSetupAction
    data class OnRoundsChanged(val change: SettingChange) : WorkoutSetupAction
    data object OnStartWorkoutClick : WorkoutSetupAction
}

