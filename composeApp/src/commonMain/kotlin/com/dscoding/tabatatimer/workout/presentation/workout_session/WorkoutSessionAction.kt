package com.dscoding.tabatatimer.workout.presentation.workout_session

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem

sealed interface WorkoutSessionAction {
    data class OnSessionSetup(val sessionItems: List<WorkoutSessionItem>) : WorkoutSessionAction
}