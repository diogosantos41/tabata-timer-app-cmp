package com.dscoding.tabatatimer.workout.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dscoding.tabatatimer.workout.presentation.workout_session.WorkoutSessionRoot
import com.dscoding.tabatatimer.workout.presentation.workout_setup.WorkoutSetupRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: NavigationRoute = NavigationRoute.WorkoutSetup
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavigationRoute.WorkoutSetup> {
            WorkoutSetupRoot(
                onStartWorkout = {
                    navController.navigate(
                        NavigationRoute.WorkoutSession
                    )
                }
            )
        }
        composable<NavigationRoute.WorkoutSession> {
            WorkoutSessionRoot(
                onWorkoutSessionFinished = {
                    navController.navigate(NavigationRoute.WorkoutFinished)
                },
                onGoBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavigationRoute.WorkoutFinished> {
            Box(modifier = Modifier.fillMaxSize().background(Green.copy(alpha = 0.25f)))
        }
    }
}