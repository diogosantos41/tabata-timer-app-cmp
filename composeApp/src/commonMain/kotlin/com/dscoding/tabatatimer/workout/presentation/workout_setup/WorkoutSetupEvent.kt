package com.dscoding.tabatatimer.workout.presentation.workout_setup

sealed interface WorkoutSetupEvent {
    data class OnStartWorkout(val workTime: Int, val restTime: Int, val rounds: Int) : WorkoutSetupEvent
}