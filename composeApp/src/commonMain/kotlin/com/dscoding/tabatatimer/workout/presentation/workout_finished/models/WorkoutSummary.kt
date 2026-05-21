package com.dscoding.tabatatimer.workout.presentation.workout_finished.models

data class WorkoutSummary(
    val formattedDuration: String,
    val rounds: Int,
    val calories: Int,
)
