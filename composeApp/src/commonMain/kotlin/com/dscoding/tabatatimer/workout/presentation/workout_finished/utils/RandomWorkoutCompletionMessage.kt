package com.dscoding.tabatatimer.workout.presentation.workout_finished.utils

import com.dscoding.tabatatimer.core.presentation.utils.UiText
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.workout_msg_1
import tabatatimer.composeapp.generated.resources.workout_msg_10
import tabatatimer.composeapp.generated.resources.workout_msg_11
import tabatatimer.composeapp.generated.resources.workout_msg_12
import tabatatimer.composeapp.generated.resources.workout_msg_13
import tabatatimer.composeapp.generated.resources.workout_msg_14
import tabatatimer.composeapp.generated.resources.workout_msg_15
import tabatatimer.composeapp.generated.resources.workout_msg_2
import tabatatimer.composeapp.generated.resources.workout_msg_3
import tabatatimer.composeapp.generated.resources.workout_msg_4
import tabatatimer.composeapp.generated.resources.workout_msg_5
import tabatatimer.composeapp.generated.resources.workout_msg_6
import tabatatimer.composeapp.generated.resources.workout_msg_7
import tabatatimer.composeapp.generated.resources.workout_msg_8
import tabatatimer.composeapp.generated.resources.workout_msg_9

fun getRandomWorkoutCompletionMessage(): UiText {
    return listOf(
        UiText.Resource(Res.string.workout_msg_1),
        UiText.Resource(Res.string.workout_msg_2),
        UiText.Resource(Res.string.workout_msg_3),
        UiText.Resource(Res.string.workout_msg_4),
        UiText.Resource(Res.string.workout_msg_5),
        UiText.Resource(Res.string.workout_msg_6),
        UiText.Resource(Res.string.workout_msg_7),
        UiText.Resource(Res.string.workout_msg_8),
        UiText.Resource(Res.string.workout_msg_9),
        UiText.Resource(Res.string.workout_msg_10),
        UiText.Resource(Res.string.workout_msg_11),
        UiText.Resource(Res.string.workout_msg_12),
        UiText.Resource(Res.string.workout_msg_13),
        UiText.Resource(Res.string.workout_msg_14),
        UiText.Resource(Res.string.workout_msg_15),
    ).random()
}