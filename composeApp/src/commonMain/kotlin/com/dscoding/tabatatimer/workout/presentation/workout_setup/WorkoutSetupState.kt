package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.core.presentation.utils.toTimeFormat
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi

data class WorkoutSetupState(
    val selectedWorkTime: TimeUi? = null,
    val selectedRestTime: TimeUi? = null,
    val selectedRounds: Int? = null,
) {
    private val workSecondsTotal: Int
        get() = (selectedWorkTime?.seconds ?: 0) * (selectedRounds ?: 0)

    private val restSecondsTotal: Int
        get() = (selectedRestTime?.seconds ?: 0) * (selectedRounds ?: 0)

    private val workoutSecondsTotal: Int
        get() = workSecondsTotal + restSecondsTotal

    val totalWorkTime: String
        get() = workSecondsTotal.toTimeFormat()

    val totalRestTime: String
        get() = restSecondsTotal.toTimeFormat()

    val totalWorkoutTime: String
        get() = workoutSecondsTotal.toTimeFormat()

    val workTimeRatio: Float
        get() = if (workoutSecondsTotal == 0) 0f
        else workSecondsTotal.toFloat() / workoutSecondsTotal.toFloat()
}