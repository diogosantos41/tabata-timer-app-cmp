package com.dscoding.tabatatimer.workout.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object WorkoutSetup : NavigationRoute

    @Serializable
    data class WorkoutSession(
        val workTime: Int,
        val restTime: Int,
        val rounds: Int
    ) : NavigationRoute

    @Serializable
    data object WorkoutFinished : NavigationRoute
}