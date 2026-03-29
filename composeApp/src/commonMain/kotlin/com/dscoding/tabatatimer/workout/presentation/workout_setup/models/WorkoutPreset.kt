package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.classic
import tabatatimer.composeapp.generated.resources.endurance
import tabatatimer.composeapp.generated.resources.quick

enum class WorkoutPreset(val title: UiText, val workSeconds: Int, val restSeconds: Int, val rounds: Int) {
    Classic(
        title = UiText.Resource(Res.string.classic),
        workSeconds = 20,
        restSeconds = 10,
        rounds = 8
    ),
    Endurance(
        title = UiText.Resource(Res.string.endurance),
        workSeconds = 40,
        restSeconds = 15,
        rounds = 8
    ),
    Quick(
        title = UiText.Resource(Res.string.quick),
        workSeconds = 15,
        restSeconds = 5,
        rounds = 4
    );

    val display: String
        get() = "${workSeconds}s / ${restSeconds}s / $rounds"

    fun matches(workSeconds: Int, restSeconds: Int, rounds: Int): Boolean {
        return this.workSeconds == workSeconds &&
                this.restSeconds == restSeconds &&
                this.rounds == rounds
    }
}

val defaultPreset = WorkoutPreset.Classic