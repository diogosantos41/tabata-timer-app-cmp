package com.dscoding.tabatatimer.workout.presentation.workout_finished

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.presentation.workout_finished.models.WorkoutSummary

data class WorkoutFinishedState(
    val completionMessage: UiText? = null,
    val summary: WorkoutSummary? = null,
)
