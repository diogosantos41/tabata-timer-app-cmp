package com.dscoding.tabatatimer.workout.domain.timer

import kotlinx.coroutines.flow.StateFlow

interface CountdownTimer {
    val remainingMillis: StateFlow<Long?>

    fun start(
        totalSeconds: Int,
        onComplete: () -> Unit
    )

    fun pause()
    fun resume()
    fun stop()
}