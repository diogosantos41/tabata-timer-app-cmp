package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.core.presentation.utils.toTimeFormat
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.defaultPreset

data class WorkoutSetupState(
    val selectedWorkTime: TimeUi = TimeUi(defaultPreset.workSeconds),
    val selectedRestTime: TimeUi = TimeUi(defaultPreset.restSeconds),
    val selectedRounds: Int = defaultPreset.rounds,
) {
    private val workSecondsTotal: Int
        get() = selectedWorkTime.seconds * selectedRounds

    private val restSecondsTotal: Int
        get() = selectedRestTime.seconds * selectedRounds

    private val workoutSecondsTotal: Int
        get() = workSecondsTotal + restSecondsTotal

    val formattedTotalWorkSeconds: String
        get() = workSecondsTotal.toTimeFormat()

    val formattedTotalRestSeconds: String
        get() = restSecondsTotal.toTimeFormat()

    val formattedTotalWorkoutSeconds: String
        get() = workoutSecondsTotal.toTimeFormat()

    val workRatio: Float
        get() = if (workoutSecondsTotal == 0) 0f
        else workSecondsTotal.toFloat() / workoutSecondsTotal.toFloat()
}