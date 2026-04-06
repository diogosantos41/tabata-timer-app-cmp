package com.dscoding.tabatatimer.workout.presentation.workout_session

sealed interface WorkoutSessionAction {
    data object OnSkipExerciseClick : WorkoutSessionAction
    data object OnResumePauseClick : WorkoutSessionAction
    data object OnStopWorkoutClick : WorkoutSessionAction
    data object OnToggleSoundClick : WorkoutSessionAction
}