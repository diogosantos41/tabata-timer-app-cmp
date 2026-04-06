package com.dscoding.tabatatimer.core.domain

import kotlinx.coroutines.flow.Flow

interface WorkoutPreferences {
    suspend fun setLastWorkoutSettings(workSeconds: Int, restSeconds: Int, rounds: Int)
    fun observeLastWorkoutSettings(): Flow<Triple<WorkSeconds, RestSeconds, Rounds>>
    suspend fun setSoundEnabled(isEnabled: Boolean)
    fun observeSoundEnabled(): Flow<Boolean>
    suspend fun setDarkModeEnabled(isEnabled: Boolean)
    fun observeDarkModeEnabled(): Flow<Boolean>
}

typealias WorkSeconds = Int?
typealias RestSeconds = Int?
typealias Rounds = Int?