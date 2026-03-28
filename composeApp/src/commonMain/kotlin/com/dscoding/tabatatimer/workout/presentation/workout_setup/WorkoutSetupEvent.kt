package com.dscoding.tabatatimer.workout.presentation.workout_setup

sealed interface WorkoutSetupEvent {
    data class OnStartWorkout(val work: Int, val rest: Int, val rounds: Int) : WorkoutSetupEvent
}