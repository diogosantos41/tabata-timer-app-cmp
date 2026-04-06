package com.dscoding.tabatatimer.workout.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.tabatatimer.data.audio.AndroidWorkoutAudio
import com.dscoding.tabatatimer.data.datastore.createDataStore
import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformDataModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
    single<WorkoutAudio> {
        AndroidWorkoutAudio(androidContext(), get(), get())
    }
}