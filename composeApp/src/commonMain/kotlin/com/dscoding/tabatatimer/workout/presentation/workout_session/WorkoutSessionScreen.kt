package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataBar
import com.dscoding.tabatatimer.core.presentation.components.TabataIconButton
import com.dscoding.tabatatimer.core.presentation.components.TabataPrimaryButton
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.LargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.SmallSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.core.presentation.utils.ObserveAsEvents
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.core.presentation.utils.color
import com.dscoding.tabatatimer.core.presentation.utils.toTimeFormat
import com.dscoding.tabatatimer.workout.presentation.workout_session.components.TabataWorkoutTimer
import com.dscoding.tabatatimer.workout.presentation.workout_session.models.TimerPlayState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.round_progress
import tabatatimer.composeapp.generated.resources.skip_exercise
import tabatatimer.composeapp.generated.resources.starting
import tabatatimer.composeapp.generated.resources.stop_workout_session
import tabatatimer.composeapp.generated.resources.work

@Composable
fun WorkoutSessionRoot(
    viewModel: WorkoutSessionViewModel = koinViewModel(),
    onWorkoutSessionFinished: () -> Unit,
    onGoBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            WorkoutSessionEvent.SessionCompleted -> onWorkoutSessionFinished()
            WorkoutSessionEvent.StopWorkout -> onGoBack()
        }
    }

    WorkoutSessionScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun WorkoutSessionScreen(
    state: WorkoutSessionState,
    onAction: (WorkoutSessionAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = NormalSpacing)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(LargeSpacing))
            Text(
                text = stringResource(
                    Res.string.round_progress,
                    state.currentWorkoutSessionItem.round,
                    state.rounds
                ),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            TabataBar(progress = state.roundProgress, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(0.5f))
            TabataWorkoutTimer(
                progress = state.timeProgress,
                timeRemaining = state.roundSecondsRemaining.toTimeFormat(),
                currentWorkout = state.currentWorkoutSessionItem.description.asString(),
                nextWorkout = state.nextWorkoutDescription.asString(),
                color = state.currentWorkoutSessionItem.workoutType.color(),
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = LargeSpacing),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TabataIconButton(
                    iconImageVector = Icons.Default.Stop,
                    contentDescription = stringResource(Res.string.stop_workout_session),
                    onClick = { onAction(WorkoutSessionAction.OnStopWorkoutClick) }
                )
                TabataPrimaryButton(
                    text = state.pausePlayButtonText.asString(),
                    onClick = { onAction(WorkoutSessionAction.OnResumePauseClick) },
                    color = state.currentWorkoutSessionItem.workoutType.color(),
                    iconImageVector =
                        if (state.currentTimerPlayState == TimerPlayState.Running)
                            Icons.Default.Pause
                        else
                            Icons.Default.PlayArrow
                )
                TabataIconButton(
                    iconImageVector = Icons.Default.SkipNext,
                    contentDescription = stringResource(Res.string.skip_exercise),
                    onClick = { onAction(WorkoutSessionAction.OnSkipExerciseClick) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun WorkoutSessionScreenPreview() {
    TabataTimerTheme {
        WorkoutSessionScreen(
            state = WorkoutSessionState(
                rounds = 10,
                currentWorkoutSessionItem = WorkoutSessionItem(
                    description = UiText.Resource(Res.string.work),
                    seconds = 120,
                    workoutType = WorkoutType.Work,
                    round = 1
                ),
                currentTimerPlayState = TimerPlayState.Running,
                nextWorkoutDescription = UiText.DynamicString("Finished"),
                roundSecondsRemaining = 10,
            ),
            onAction = {}
        )
    }
}