package com.dscoding.tabatatimer.workout.data.timer
import com.dscoding.tabatatimer.workout.domain.CountdownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DefaultCountdownTimer(
    private val applicationScope: CoroutineScope
) : CountdownTimer {

    private val _remainingSeconds = MutableStateFlow<Int?>(null)
    override val remainingSeconds = _remainingSeconds.asStateFlow()

    private var timerJob: Job? = null
    private var onComplete: (() -> Unit)? = null

    override fun start(
        totalSeconds: Int,
        onComplete: () -> Unit
    ) {
        stop()

        this.onComplete = onComplete
        _remainingSeconds.value = totalSeconds

        runTimer()
    }

    override fun pause() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun resume() {
        val currentRemaining = _remainingSeconds.value ?: return
        if (currentRemaining <= 0 || timerJob != null) return

        runTimer()
    }

    override fun stop() {
        timerJob?.cancel()
        timerJob = null
        _remainingSeconds.value = null
        onComplete = null
    }

    private fun runTimer() {
        timerJob?.cancel()
        timerJob = applicationScope.launch {
            while (true) {
                val currentRemaining = _remainingSeconds.value ?: break

                if (currentRemaining <= 0) {
                    onComplete?.invoke()
                    timerJob = null
                    break
                }

                delay(1000L)

                val updatedRemaining = ((_remainingSeconds.value ?: 0) - 1).coerceAtLeast(0)
                _remainingSeconds.value = updatedRemaining

                if (updatedRemaining == 0) {
                    onComplete?.invoke()
                    timerJob = null
                    break
                }
            }
        }
    }
}