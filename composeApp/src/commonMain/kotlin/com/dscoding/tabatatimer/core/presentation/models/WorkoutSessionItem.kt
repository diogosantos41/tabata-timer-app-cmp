package com.dscoding.tabatatimer.core.presentation.models

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.presentation.workout_session.models.WorkoutType
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutSessionItem(
    val description: UiText,
    val seconds: Int,
    val workoutType: WorkoutType,
    val round: Int? = null
)
