package com.dscoding.tabatatimer.workout.domain

import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import com.dscoding.tabatatimer.workout.domain.audio.models.SoundEffect
import com.dscoding.tabatatimer.workout.domain.session.SessionPlayState
import com.dscoding.tabatatimer.workout.domain.session.SessionStep
import com.dscoding.tabatatimer.workout.domain.session.SessionEngineState
import com.dscoding.tabatatimer.workout.domain.timer.CountdownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutSessionCoordinator(
    private val countdownTimer: CountdownTimer,
    private val workoutAudio: WorkoutAudio,
) {

    private var steps: List<SessionStep> = emptyList()
    private var index = 0
    private var tickJob: Job? = null
    private var onSessionCompleted: (() -> Unit)? = null

    private val _engineState = MutableStateFlow(SessionEngineState())
    val engineState = _engineState.asStateFlow()

    fun start(
        sessionScope: CoroutineScope,
        steps: List<SessionStep>,
        onSessionCompleted: () -> Unit,
    ) {
        release()
        if (steps.isEmpty()) return

        this.steps = steps
        this.index = 0
        this.onSessionCompleted = onSessionCompleted

        startObservingTicks(sessionScope)
        pushEngineState()
        startTimerForCurrentStep()
    }

    fun togglePauseResume() {
        if (steps.isEmpty()) return
        when (engineState.value.playState) {
            SessionPlayState.Running -> {
                countdownTimer.pause()
                _engineState.update { it.copy(playState = SessionPlayState.Paused) }
            }

            SessionPlayState.Paused -> {
                countdownTimer.resume()
                _engineState.update { it.copy(playState = SessionPlayState.Running) }
            }
        }
    }

    fun skipCurrentStep() {
        if (steps.isEmpty()) return
        countdownTimer.stop()
        advanceToNextOrFinish()
    }

    fun release() {
        tickJob?.cancel()
        tickJob = null
        countdownTimer.stop()
        onSessionCompleted = null
        steps = emptyList()
        index = 0
        _engineState.value = SessionEngineState()
    }

    private fun startObservingTicks(sessionScope: CoroutineScope) {
        tickJob?.cancel()
        tickJob = sessionScope.launch {
            countdownTimer.remainingMillis.collect { millis ->
                _engineState.update {
                    it.copy(remainingMillis = millis ?: 0L)
                }
            }
        }
    }

    private fun startTimerForCurrentStep() {
        val step = steps.getOrNull(index) ?: return
        countdownTimer.start(
            totalSeconds = step.seconds,
            onComplete = {
                workoutAudio.playSoundEffect(SoundEffect.WHISTLE)
                advanceToNextOrFinish()
            },
        )
    }

    private fun advanceToNextOrFinish() {
        if (index >= steps.lastIndex) {
            completeSession()
            return
        }
        index++
        pushEngineState()
        startTimerForCurrentStep()
    }

    private fun completeSession() {
        tickJob?.cancel()
        tickJob = null
        countdownTimer.stop()
        onSessionCompleted?.invoke()
    }

    private fun pushEngineState() {
        _engineState.update {
            it.copy(
                currentIndex = index,
                playState = SessionPlayState.Running,
            )
        }
    }
}
