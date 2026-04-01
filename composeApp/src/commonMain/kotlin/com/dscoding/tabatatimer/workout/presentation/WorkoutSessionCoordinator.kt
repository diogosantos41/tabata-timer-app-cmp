package com.dscoding.tabatatimer.workout.presentation

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.rest
import tabatatimer.composeapp.generated.resources.starting
import tabatatimer.composeapp.generated.resources.work

class WorkoutSessionCoordinator {
    private var currentSessionItems: List<WorkoutSessionItem>? = null

    fun buildSetWorkoutSession(
        workSeconds: Int,
        restSeconds: Int,
        rounds: Int
    ) {
        currentSessionItems = buildList {
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

    fun getSessionItems(): List<WorkoutSessionItem>? = currentSessionItems

    fun clearSession() {
        currentSessionItems = null
    }
}