package com.dscoding.tabatatimer.workout.presentation.workout_finished.utils

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSession
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.utils.toTimeFormat
import com.dscoding.tabatatimer.workout.presentation.workout_finished.models.WorkoutSummary
import kotlin.math.roundToInt

private const val WORK_CALORIES_PER_MINUTE = 15.0
private const val REST_CALORIES_PER_MINUTE = 5.0
private const val SECONDS_PER_MINUTE = 60.0

fun calculateWorkoutSummary(session: WorkoutSession?): WorkoutSummary? {
    val items = session?.items.orEmpty()
    if (items.isEmpty()) return null

    val workSeconds = items
        .filter { it.workoutType == WorkoutType.Work }
        .sumOf { it.seconds }
    val restSeconds = items
        .filter { it.workoutType == WorkoutType.Rest }
        .sumOf { it.seconds }

    val durationSeconds = workSeconds + restSeconds
    val calories = (
        (workSeconds / SECONDS_PER_MINUTE) * WORK_CALORIES_PER_MINUTE +
            (restSeconds / SECONDS_PER_MINUTE) * REST_CALORIES_PER_MINUTE
        ).roundToInt()
    val rounds = items.lastOrNull()?.round ?: 0

    return WorkoutSummary(
        formattedDuration = durationSeconds.toTimeFormat(),
        rounds = rounds,
        calories = calories,
    )
}
