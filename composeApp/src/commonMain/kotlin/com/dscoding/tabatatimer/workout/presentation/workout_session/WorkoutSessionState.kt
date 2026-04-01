package com.dscoding.tabatatimer.workout.presentation.workout_session

import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.presentation.workout_session.models.TimerPlayState
import org.jetbrains.compose.resources.stringResource
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.pause
import tabatatimer.composeapp.generated.resources.resume
import tabatatimer.composeapp.generated.resources.starting
import tabatatimer.composeapp.generated.resources.work

data class WorkoutSessionState(
    val currentRound: Int = 1,
    val rounds: Int = Int.MAX_VALUE,
    val currentWorkoutDescription: UiText = UiText.Resource(Res.string.starting),
    val currentWorkoutType: WorkoutType = WorkoutType.Starting,
    val nextWorkoutDescription: UiText = UiText.Resource(Res.string.work),
    val currentTimerPlayState: TimerPlayState = TimerPlayState.Running,
    val roundSecondsRemaining: Int = Int.MAX_VALUE,
    val roundTotalSeconds: Int = Int.MAX_VALUE
) {
    val roundProgress: Float
        get() = if (rounds == 0) 0f
        else currentRound.toFloat() / rounds.toFloat()

    val timeProgress: Float
        get() = if (roundTotalSeconds == 0) 0f
        else 1f - (roundSecondsRemaining.toFloat() / roundTotalSeconds.toFloat())

    val pausePlayButtonText = when(currentTimerPlayState) {
        TimerPlayState.Running -> UiText.Resource(Res.string.pause)
        TimerPlayState.Paused -> UiText.Resource(Res.string.resume)
    }
}