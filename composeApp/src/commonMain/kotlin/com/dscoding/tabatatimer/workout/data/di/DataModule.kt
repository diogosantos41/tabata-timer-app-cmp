package com.dscoding.tabatatimer.workout.data.di

import com.dscoding.tabatatimer.workout.data.timer.DefaultCountdownTimer
import com.dscoding.tabatatimer.workout.domain.CountdownTimer
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformDataModule: Module

val dataModule = module {
    includes(platformDataModule)
    singleOf(::DefaultCountdownTimer) bind CountdownTimer::class
}