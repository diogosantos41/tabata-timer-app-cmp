package com.dscoding.tabatatimer.core.presentation.models

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import kotlinx.serialization.Serializable

data class WorkoutSessionItem(
    val description: UiText,
    val seconds: Int,
    val workoutType: WorkoutType,
    val round: Int,
)
