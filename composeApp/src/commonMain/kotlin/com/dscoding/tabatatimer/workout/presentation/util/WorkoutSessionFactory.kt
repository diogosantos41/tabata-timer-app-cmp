package com.dscoding.tabatatimer.workout.presentation.util

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.rest
import tabatatimer.composeapp.generated.resources.starting
import tabatatimer.composeapp.generated.resources.work

class WorkoutSessionFactory {

    fun buildWorkoutSession(
        workSeconds: Int,
        restSeconds: Int,
        rounds: Int
    ): List<WorkoutSessionItem> {
        return buildList {
            add(
                WorkoutSessionItem(
                    description = UiText.Resource(Res.string.starting),
                    seconds = 5,
                    workoutType = WorkoutType.Starting,
                    round = 1
                )
            )

            repeat(rounds) { index ->
                val round = index + 1

                add(
                    WorkoutSessionItem(
                        description = UiText.Resource(Res.string.work),
                        seconds = workSeconds,
                        workoutType = WorkoutType.Work,
                        round = round
                    )
                )

                if (round < rounds) {
                    add(
                        WorkoutSessionItem(
                            description = UiText.Resource(Res.string.rest),
                            seconds = restSeconds,
                            workoutType = WorkoutType.Rest,
                            round = round
                        )
                    )
                }
            }
        }
    }
}