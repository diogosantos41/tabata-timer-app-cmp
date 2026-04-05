package com.dscoding.tabatatimer.workout.data.timer

import com.dscoding.tabatatimer.workout.domain.CountdownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.TimeSource

class DefaultCountdownTimer(
    private val applicationScope: CoroutineScope
) : CountdownTimer {

    private val _remainingMillis = MutableStateFlow<Long?>(null)
    override val remainingMillis = _remainingMillis.asStateFlow()

    private var timerJob: Job? = null
    private var onComplete: (() -> Unit)? = null

    private var remainingMillisOnPause: Long = 0L
    private var startedAt = TimeSource.Monotonic.markNow()

    override fun start(
        totalSeconds: Int,
        onComplete: () -> Unit
    ) {
        stop()

        this.onComplete = onComplete
        remainingMillisOnPause = totalSeconds.coerceAtLeast(0) * 1000L
        _remainingMillis.value = remainingMillisOnPause
        startedAt = TimeSource.Monotonic.markNow()

        runTimer()
    }

    override fun pause() {
        remainingMillisOnPause = currentRemainingMillis().coerceAtLeast(0L)

        timerJob?.cancel()
        timerJob = null
    }

    override fun resume() {
        if (remainingMillisOnPause <= 0L || timerJob != null) return

        startedAt = TimeSource.Monotonic.markNow()
        _remainingMillis.value = remainingMillisOnPause

        runTimer()
    }

    override fun stop() {
        timerJob?.cancel()
        timerJob = null
        _remainingMillis.value = null
        onComplete = null
        remainingMillisOnPause = 0L
    }

    private fun runTimer() {
        timerJob?.cancel()
        timerJob = applicationScope.launch {
            while (currentCoroutineContext().isActive) {
                val remainingMillis = currentRemainingMillis()

                if (remainingMillis <= 0L) {
                    _remainingMillis.value = 0L
                    timerJob = null
                    onComplete?.invoke()
                    break
                }

                _remainingMillis.value = remainingMillis

                delay(16L)
            }
        }
    }

    private fun currentRemainingMillis(): Long {
        val elapsedMillis = startedAt.elapsedNow().inWholeMilliseconds
        return remainingMillisOnPause - elapsedMillis
    }
}