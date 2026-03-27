package com.dscoding.tabatatimer.workout.presentation.workout_setup.models

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.classic
import tabatatimer.composeapp.generated.resources.endurance
import tabatatimer.composeapp.generated.resources.quick

enum class WorkoutPreset(val title: UiText, val work: Int, val rest: Int, val rounds: Int) {
    Classic(
        title = UiText.StringResourceId(Res.string.classic),
        work = 20,
        rest = 10,
        rounds = 8
    ),
    Endurance(
        title = UiText.StringResourceId(Res.string.endurance),
        work = 40,
        rest = 15,
        rounds = 8
    ),
    Quick(
        title = UiText.StringResourceId(Res.string.quick),
        work = 15,
        rest = 5,
        rounds = 4
    );

    val displayInfo: String
        get() = "${work}s / ${rest}s / $rounds"
}

val defaultPreset = WorkoutPreset.Classic