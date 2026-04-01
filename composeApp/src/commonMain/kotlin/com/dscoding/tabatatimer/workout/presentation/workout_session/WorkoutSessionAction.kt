package com.dscoding.tabatatimer.workout.presentation.workout_session

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem

sealed interface WorkoutSessionAction {
    data object OnSkipExerciseClick : WorkoutSessionAction
    data object OnResumePauseClick : WorkoutSessionAction
    data object OnStopWorkoutClick : WorkoutSessionAction
}