package com.dscoding.tabatatimer.workout.presentation.workout_session.utils

import kotlin.math.ceil

fun Int.secondsToMillis(): Long = this * 1000L

fun Long.millisToSecondsCeil(): Int {
    if (this <= 0L) return 0
    return ceil(this / 1000.0).toInt()
}