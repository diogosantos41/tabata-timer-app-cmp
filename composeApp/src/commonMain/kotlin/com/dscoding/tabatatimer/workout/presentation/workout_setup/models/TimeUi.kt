package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

data class TimeUi(
    val seconds: Int
) {
    val displayTime: String
        get() = "${seconds}s"
}