package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.WorkoutPreset

sealed interface WorkoutSetupAction {
    data class OnPresetClick(val preset: WorkoutPreset) : WorkoutSetupAction
    data object OnWorkTimeChanged : WorkoutSetupAction
    data object OnRestTimeChanged : WorkoutSetupAction
    data object OnRoundsChanged : WorkoutSetupAction
    data object OnStartWorkoutClick : WorkoutSetupAction
}