package com.dscoding.tabatatimer.workout.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object WorkoutSetup : NavigationRoute

    @Serializable
    data object WorkoutSession : NavigationRoute

    @Serializable
    data object WorkoutFinished : NavigationRoute
}