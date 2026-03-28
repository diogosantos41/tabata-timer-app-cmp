package com.dscoding.tabatatimer.workout.presentation.workout_setup

import com.dscoding.tabatatimer.core.presentation.utils.toTimeFormat
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.defaultPreset

data class WorkoutSetupState(
    val selectedWorkTime: TimeUi = TimeUi(defaultPreset.workTime),
    val selectedRestTime: TimeUi = TimeUi(defaultPreset.restTime),
    val selectedRounds: Int = defaultPreset.rounds,
) {
    private val workSecondsTotal: Int
        get() = selectedWorkTime.seconds * selectedRounds

    private val restSecondsTotal: Int
        get() = selectedRestTime.seconds * selectedRounds

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