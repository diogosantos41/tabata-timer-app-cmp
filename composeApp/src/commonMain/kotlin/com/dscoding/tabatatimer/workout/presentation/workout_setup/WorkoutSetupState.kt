package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi

data class WorkoutSetupState(
    val selectedWorkTime: TimeUi? = null,
    val selectedRestTime: TimeUi? = null,
    val selectedRounds: Int? = null,
    val totalWorkTime: String? = null,
    val totalRestTime: String? = null,
    val totalWorkoutTime: String? = null,
)