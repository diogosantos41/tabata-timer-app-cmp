package com.dscoding.tabatatimer.workout.presentation.util

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem

class WorkoutSessionStore {
    private var currentSessionItems: List<WorkoutSessionItem>? = null

    fun setSessionItems(items: List<WorkoutSessionItem>) {
        currentSessionItems = items
    }

    fun getSessionItems(): List<WorkoutSessionItem>? = currentSessionItems

    fun clearSession() {
        currentSessionItems = null
    }
}