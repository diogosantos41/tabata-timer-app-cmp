package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.classic
import tabatatimer.composeapp.generated.resources.endurance
import tabatatimer.composeapp.generated.resources.quick

enum class WorkoutPreset(val title: UiText, val workTime: Int, val restTime: Int, val rounds: Int) {
    Classic(
        title = UiText.StringResourceId(Res.string.classic),
        workTime = 20,
        restTime = 10,
        rounds = 8
    ),
    Endurance(
        title = UiText.StringResourceId(Res.string.endurance),
        workTime = 40,
        restTime = 15,
        rounds = 8
    ),
    Quick(
        title = UiText.StringResourceId(Res.string.quick),
        workTime = 15,
        restTime = 5,
        rounds = 4
    );

    val display: String
        get() = "${workTime}s / ${restTime}s / $rounds"

    fun matches(workTime: Int, restTime: Int, rounds: Int): Boolean {
        return this.workTime == workTime &&
                this.restTime == restTime &&
                this.rounds == rounds
    }
}

val defaultPreset = WorkoutPreset.Classic