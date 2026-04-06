package com.dscoding.tabatatimer.workout.presentation.workout_session

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.presentation.workout_session.models.TimerPlayState
import com.dscoding.tabatatimer.workout.presentation.workout_session.utils.millisToSecondsCeil
import com.dscoding.tabatatimer.workout.presentation.workout_session.utils.secondsToMillis
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.starting
import tabatatimer.composeapp.generated.resources.work


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
    val roundMillisRemaining: Long = Long.MAX_VALUE,
    val isSoundEnabled: Boolean = false
) {
    val roundSecondsRemaining: Int
        get() = roundMillisRemaining.millisToSecondsCeil()

    val roundProgress: Float
        get() = if (rounds == 0) 0f
        else currentWorkoutSessionItem.round.toFloat() / rounds.toFloat()

    val timeProgress: Float
        get() {
            val totalMillis = currentWorkoutSessionItem.seconds.secondsToMillis()
            if (totalMillis <= 0L) return 0f

            return (1f - (roundMillisRemaining.toFloat() / totalMillis.toFloat()))
                .coerceIn(0f, 1f)
        }
}