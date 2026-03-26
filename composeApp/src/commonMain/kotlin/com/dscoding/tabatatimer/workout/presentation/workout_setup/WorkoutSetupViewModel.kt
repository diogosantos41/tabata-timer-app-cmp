package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class WorkoutSetupViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WorkoutSetupState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorkoutSetupState()
        )

    fun onAction(action: WorkoutSetupAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}