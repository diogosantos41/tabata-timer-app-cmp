package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataBar
import com.dscoding.tabatatimer.core.presentation.components.TabataIconButton
import com.dscoding.tabatatimer.core.presentation.components.TabataButton
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.LargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.SmallSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.core.presentation.utils.ObserveAsEvents
import com.dscoding.tabatatimer.core.presentation.utils.UiText
import com.dscoding.tabatatimer.core.presentation.utils.color
import com.dscoding.tabatatimer.workout.domain.session.SessionPlayState
import com.dscoding.tabatatimer.workout.presentation.workout_session.components.TabataWorkoutTimer
import com.dscoding.tabatatimer.workout.presentation.workout_session.utils.secondsToMillis
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.finish
import tabatatimer.composeapp.generated.resources.pause
import tabatatimer.composeapp.generated.resources.resume
import tabatatimer.composeapp.generated.resources.round_progress
import tabatatimer.composeapp.generated.resources.skip_exercise
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
            Spacer(modifier = Modifier.height(NormalSpacing))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(
                        Res.string.round_progress,
                        state.currentWorkoutSessionItem.round,
                        state.rounds
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                )
                TabataIconButton(
                    iconImageVector = if (state.isSoundEnabled)
                        Icons.AutoMirrored.Filled.VolumeUp
                    else
                        Icons.AutoMirrored.Filled.VolumeOff,
                    contentDescription = stringResource(Res.string.skip_exercise),
                    onClick = { onAction(WorkoutSessionAction.OnToggleSoundClick) }
                )
            }

            Spacer(modifier = Modifier.height(SmallSpacing))
            TabataBar(
                progress = state.roundProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = LargeSpacing)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            TabataWorkoutTimer(
                progress = state.timeProgress,
                timeRemaining = "${state.roundSecondsRemaining}",
                currentWorkout = state.currentWorkoutSessionItem.description.asString(),
                nextWorkout = state.nextWorkoutDescription.asString(),
                color =
                    if (state.currentSessionPlayState == SessionPlayState.Running)
                        state.currentWorkoutSessionItem.workoutType.color()
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.widthIn(max = 400.dp)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = LargeSpacing),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TabataIconButton(
                    iconImageVector = Icons.Default.Stop,
                    contentDescription = stringResource(Res.string.stop_workout_session),
                    onClick = { onAction(WorkoutSessionAction.OnStopWorkoutClick) }
                )
                TabataButton(
                    text =
                        if (state.currentSessionPlayState == SessionPlayState.Running)
                            stringResource(Res.string.pause)
                        else
                            stringResource(Res.string.resume),
                    onClick = { onAction(WorkoutSessionAction.OnResumePauseClick) },
                    color = state.currentWorkoutSessionItem.workoutType.color(),
                    iconImageVector =
                        if (state.currentSessionPlayState == SessionPlayState.Running)
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
                currentSessionPlayState = SessionPlayState.Running,
                nextWorkoutDescription = UiText.Resource(Res.string.finish),
                roundMillisRemaining = 15.secondsToMillis(),
                isSoundEnabled = true
            ),
            onAction = {}
        )
    }
}