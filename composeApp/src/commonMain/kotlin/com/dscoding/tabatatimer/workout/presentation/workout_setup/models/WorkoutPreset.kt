package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.classic
import tabatatimer.composeapp.generated.resources.endurance
import tabatatimer.composeapp.generated.resources.light
import tabatatimer.composeapp.generated.resources.preset_description

enum class WorkoutPreset(
    val title: UiText,
    val workSeconds: Int,
    val restSeconds: Int,
    val rounds: Int
) {
    Classic(
        title = UiText.Resource(Res.string.classic),
        workSeconds = 20,
        restSeconds = 10,
        rounds = 8
    ),
    Endurance(
        title = UiText.Resource(Res.string.endurance),
        workSeconds = 40,
        restSeconds = 20,
        rounds = 8
    ),
    Light(
        title = UiText.Resource(Res.string.light),
        workSeconds = 20,
        restSeconds = 20,
        rounds = 6
    );

    val display: UiText
        get() = UiText.Resource(
            Res.string.preset_description,
            arrayOf(workSeconds, restSeconds, rounds)
        )

    fun matches(workSeconds: Int, restSeconds: Int, rounds: Int): Boolean {
        return this.workSeconds == workSeconds &&
                this.restSeconds == restSeconds &&
                this.rounds == rounds
    }
}

val defaultPreset = WorkoutPreset.Classic