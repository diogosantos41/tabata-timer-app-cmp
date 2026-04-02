package com.dscoding.tabatatimer.di

import com.dscoding.tabatatimer.workout.data.di.dataModule
import com.dscoding.tabatatimer.workout.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appModule,
            presentationModule,
            dataModule
        )
    }
}