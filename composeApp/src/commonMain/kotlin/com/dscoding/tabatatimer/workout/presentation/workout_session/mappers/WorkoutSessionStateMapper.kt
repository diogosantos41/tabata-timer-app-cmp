package com.dscoding.tabatatimer.workout.presentation.workout_session.mappers

import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.workout.domain.session.SessionEngineState
import com.dscoding.tabatatimer.workout.presentation.workout_session.WorkoutSessionState
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.finish

/** Maps engine snapshot + static item list into UI state (presentation concern). */
internal fun SessionEngineState.toWorkoutSessionState(
    items: List<WorkoutSessionItem>,
    isSoundEnabled: Boolean,
): WorkoutSessionState {
    if (items.isEmpty()) {
        return WorkoutSessionState(isSoundEnabled = isSoundEnabled)
    }

    val currentItem = items.getOrNull(currentIndex)
        ?: return WorkoutSessionState(isSoundEnabled = isSoundEnabled)
    val nextItem = items.getOrNull(currentIndex + 1)
    val rounds = items.lastOrNull()?.round ?: 1

    return WorkoutSessionState(
        rounds = rounds,
        currentWorkoutSessionItem = currentItem,
        nextWorkoutDescription = nextItem?.description
            ?: UiText.Resource(Res.string.finish),
        currentSessionPlayState = playState,
        roundMillisRemaining = remainingMillis,
        isSoundEnabled = isSoundEnabled,
    )
}
