package com.dscoding.tabatatimer.workout.presentation.navigation

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object WorkoutSetup : NavigationRoute

    @Serializable
    data class WorkoutSession(
        val sessionItems: List<WorkoutSessionItem>
    ) : NavigationRoute

    @Serializable
    data object WorkoutFinished : NavigationRoute
}