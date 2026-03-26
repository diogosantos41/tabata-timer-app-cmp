package com.dscoding.tabatatimer.workout.presentation.workout_setup

data class WorkoutSetupState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)