package com.dscoding.tabatatimer.workout.presentation.di

import com.dscoding.tabatatimer.workout.presentation.WorkoutSessionCoordinator
import com.dscoding.tabatatimer.workout.presentation.workout_session.WorkoutSessionViewModel
import com.dscoding.tabatatimer.workout.presentation.workout_setup.WorkoutSetupViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::WorkoutSetupViewModel)
    viewModelOf(::WorkoutSessionViewModel)
    singleOf(::WorkoutSessionCoordinator)
}