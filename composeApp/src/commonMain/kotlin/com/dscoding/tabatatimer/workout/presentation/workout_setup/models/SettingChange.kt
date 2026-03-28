package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

enum class SettingChange(val delta: Int) {
    Increase(1),
    Decrease(-1)
}