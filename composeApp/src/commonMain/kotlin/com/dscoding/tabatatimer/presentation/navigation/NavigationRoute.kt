package com.dscoding.tabatatimer.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object WorkoutSetup : NavigationRoute

    @Serializable
    data object WorkoutTimer : NavigationRoute
}