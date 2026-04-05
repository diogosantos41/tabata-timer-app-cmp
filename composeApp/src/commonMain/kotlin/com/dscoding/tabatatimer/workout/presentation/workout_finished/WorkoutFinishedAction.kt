package com.dscoding.tabatatimer.workout.presentation.workout_finished

sealed interface WorkoutFinishedAction {
    data object OnRestartWorkoutSession : WorkoutFinishedAction
    data object OnGoBackToWorkoutSetup: WorkoutFinishedAction
}