package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

data class TimeUi(
    val seconds: Int,
    val weight: Float,
) {
    val displayTime = "${seconds}s"
}