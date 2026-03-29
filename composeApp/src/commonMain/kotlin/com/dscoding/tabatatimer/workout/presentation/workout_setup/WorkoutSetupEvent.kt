package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem

sealed interface WorkoutSetupEvent {
    data class OnStartWorkout(val sessionItems: List<WorkoutSessionItem>) : WorkoutSetupEvent

}