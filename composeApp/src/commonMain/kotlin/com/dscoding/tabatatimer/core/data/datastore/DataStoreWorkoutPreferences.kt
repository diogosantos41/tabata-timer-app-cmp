package com.dscoding.tabatatimer.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.dscoding.tabatatimer.core.domain.RestSeconds
import com.dscoding.tabatatimer.core.domain.Rounds
import com.dscoding.tabatatimer.core.domain.WorkSeconds
import com.dscoding.tabatatimer.core.domain.WorkoutPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreWorkoutPreferences(
    private val dataStore: DataStore<Preferences>
) : WorkoutPreferences {

    private val workSecondsKey = intPreferencesKey("KEY_WORK_SECONDS")
    private val restSecondsKey = intPreferencesKey("KEY_REST_SECONDS")
    private val roundsKey = intPreferencesKey("KEY_ROUNDS")

    private val soundKey = booleanPreferencesKey("KEY_SOUND")
    private val darkModeKey = booleanPreferencesKey("KEY_DARK_MODE")

    override suspend fun setLastWorkoutSettings(
        workSeconds: Int,
        restSeconds: Int,
        rounds: Int
    ) {
        dataStore.edit { prefs ->
            prefs[workSecondsKey] = workSeconds
            prefs[restSecondsKey] = restSeconds
            prefs[roundsKey] = rounds
        }
    }

    override fun observeLastWorkoutSettings(): Flow<Triple<WorkSeconds, RestSeconds, Rounds>> {
        return dataStore.data.map { prefs ->
            Triple(
                prefs[workSecondsKey],
                prefs[restSecondsKey],
                prefs[roundsKey]
            )
        }
    }

    override suspend fun setSoundEnabled(isEnabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[soundKey] = isEnabled
        }
    }

    override fun observeSoundEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[soundKey] ?: true
        }.distinctUntilChanged()
    }

    override suspend fun setDarkModeEnabled(isEnabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[darkModeKey] = isEnabled
        }
    }

    override fun observeDarkModeEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[darkModeKey] ?: true
        }.distinctUntilChanged()
    }
}