package com.dscoding.tabatatimer.workout.presentation.workout_session

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem

data class WorkoutSessionState(
    val currentWorkoutSessionItem: WorkoutSessionItem? = null,
    val workoutSessionItems: List<WorkoutSessionItem> = emptyList(),
    val roundSecondsRemaining: Int = Int.MAX_VALUE
)