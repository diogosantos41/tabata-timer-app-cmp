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
                onStartWorkout = { workTime, restTime, rounds ->
                    navController.navigate(
                        NavigationRoute.WorkoutSession(
                            workTime = workTime,
                            restTime = restTime,
                            rounds = rounds
                        )
                    )
                }
            )
        }
        composable<NavigationRoute.WorkoutSession> {
            val route = it.toRoute<NavigationRoute.WorkoutSession>()
            WorkoutSessionRoot(
                workTime = route.workTime,
                restTime = route.restTime,
                rounds = route.rounds,
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