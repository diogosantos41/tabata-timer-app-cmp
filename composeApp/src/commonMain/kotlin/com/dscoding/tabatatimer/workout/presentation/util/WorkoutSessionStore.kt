package com.dscoding.tabatatimer.workout.presentation.util

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSession

class WorkoutSessionStore {
    private var currentSession: WorkoutSession? = null

    fun setWorkoutSession(session: WorkoutSession) {
        currentSession = session
    }

    fun getWorkoutSession(): WorkoutSession? = currentSession

    fun clearSession() {
        currentSession = null
    }
}