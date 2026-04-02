package com.dscoding.tabatatimer.workout.presentation.workout_session

sealed interface WorkoutSessionEvent {
    data object StopWorkout : WorkoutSessionEvent
    data object SessionCompleted : WorkoutSessionEvent
}