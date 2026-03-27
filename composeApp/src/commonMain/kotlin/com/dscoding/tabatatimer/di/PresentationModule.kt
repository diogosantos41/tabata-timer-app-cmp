package com.dscoding.tabatatimer.di

import com.dscoding.tabatatimer.workout.presentation.workout_setup.WorkoutSetupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::WorkoutSetupViewModel)
}