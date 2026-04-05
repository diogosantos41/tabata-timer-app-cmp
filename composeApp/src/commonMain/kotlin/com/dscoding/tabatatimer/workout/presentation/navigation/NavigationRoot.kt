package com.dscoding.tabatatimer.workout.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dscoding.tabatatimer.workout.presentation.workout_finished.WorkoutFinishedRoot
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
                    navController.navigate(NavigationRoute.WorkoutFinished) {
                        popUpTo(NavigationRoute.WorkoutSetup) {
                            inclusive = false
                        }
                    }
                },
                onGoBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavigationRoute.WorkoutFinished> {
            WorkoutFinishedRoot(
                onRestartWorkoutSession = {
                    navController.navigate(NavigationRoute.WorkoutSession) {
                        popUpTo(NavigationRoute.WorkoutSetup) {
                            inclusive = false
                        }
                    }
                },
                onGoBackToWorkoutSetup = {
                    navController.popBackStack()
                }
            )
        }
    }
}