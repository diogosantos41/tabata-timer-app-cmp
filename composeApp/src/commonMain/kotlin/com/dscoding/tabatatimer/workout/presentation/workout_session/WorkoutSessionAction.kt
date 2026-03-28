package com.dscoding.tabatatimer.workout.presentation.workout_session

sealed interface WorkoutSessionAction {
    data class OnSessionSetup(val workTime: Int, val restTime: Int, val rounds: Int) : WorkoutSessionAction
}