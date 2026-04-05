package com.dscoding.tabatatimer.workout.presentation.workout_finished

sealed interface WorkoutFinishedEvent {
    data object GoBackToWorkoutSetup : WorkoutFinishedEvent
}