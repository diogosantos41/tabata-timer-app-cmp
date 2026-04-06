package com.dscoding.tabatatimer.workout.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.tabatatimer.data.audio.IosWorkoutAudio
import com.dscoding.tabatatimer.data.datastore.createDataStore
import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import org.koin.dsl.module

actual val platformDataModule = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
    single<WorkoutAudio> {
        IosWorkoutAudio(get(), get())
    }
}