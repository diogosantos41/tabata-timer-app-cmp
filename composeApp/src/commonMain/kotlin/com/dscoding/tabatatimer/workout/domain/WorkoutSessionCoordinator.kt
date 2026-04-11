package com.dscoding.tabatatimer.workout.domain

import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import com.dscoding.tabatatimer.workout.domain.audio.models.SoundEffect
import com.dscoding.tabatatimer.workout.domain.timer.CountdownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WorkoutSessionCoordinator(
    private val countdownTimer: CountdownTimer,
    private val workoutAudio: WorkoutAudio,
) {
    fun pauseTimer() {
        countdownTimer.pause()
    }

    fun resumeTimer() {
        countdownTimer.resume()
    }

    fun stopTimer() {
        countdownTimer.stop()
    }

    fun runCountdownTimer(
        scope: CoroutineScope,
        totalSeconds: Int,
        onComplete: () -> Unit,
        onTimeTick: (millis: Long) -> Unit
    ) {
        countdownTimer.start(
            totalSeconds = totalSeconds,
            onComplete = {
                workoutAudio.playSoundEffect(SoundEffect.WHISTLE)
                onComplete()
            }
        )

        countdownTimer
            .remainingMillis
            .onEach { millis ->
                onTimeTick(millis ?: 0)
            }
            .launchIn(scope)
    }
}