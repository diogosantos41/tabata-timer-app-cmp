package com.dscoding.tabatatimer.workout.presentation.workout_setup

sealed interface WorkoutSetupEvent {
    data object OnStartWorkout : WorkoutSetupEvent

}