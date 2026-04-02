package com.dscoding.tabatatimer.workout.presentation.workout_session

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.presentation.workout_session.models.TimerPlayState
import org.jetbrains.compose.resources.stringResource
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.pause
import tabatatimer.composeapp.generated.resources.resume
import tabatatimer.composeapp.generated.resources.starting
import tabatatimer.composeapp.generated.resources.work
import kotlin.Int
import kotlin.math.round

data class WorkoutSessionState(
    val rounds: Int = Int.MAX_VALUE,
    val currentWorkoutSessionItem: WorkoutSessionItem = WorkoutSessionItem(
        description = UiText.Resource(Res.string.starting),
        seconds = Int.MAX_VALUE,
        workoutType = WorkoutType.Starting,
        round = 1
    ),
    val nextWorkoutDescription: UiText = UiText.Resource(Res.string.work),
    val currentTimerPlayState: TimerPlayState = TimerPlayState.Running,
    val roundSecondsRemaining: Int = Int.MAX_VALUE,
) {
    val roundProgress: Float
        get() = if (rounds == 0) 0f
        else currentWorkoutSessionItem.round.toFloat() / rounds.toFloat()

    val timeProgress: Float
        get() = if (currentWorkoutSessionItem.seconds == 0) 0f
        else 1f - (roundSecondsRemaining.toFloat() / currentWorkoutSessionItem.seconds.toFloat())

    val pausePlayButtonText = when (currentTimerPlayState) {
        TimerPlayState.Running -> UiText.Resource(Res.string.pause)
        TimerPlayState.Paused -> UiText.Resource(Res.string.resume)
    }
}