package com.dscoding.tabatatimer.workout.presentation.workout_session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataBar
import com.dscoding.tabatatimer.core.presentation.models.WorkoutSessionItem
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.LargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.SmallSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.workout.presentation.workout_session.components.TabataWorkoutTimer
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.round_progress

@Composable
fun WorkoutSessionRoot(
    viewModel: WorkoutSessionViewModel = koinViewModel(),
    sessionItems: List<WorkoutSessionItem>,
    onWorkoutSessionFinished: () -> Unit,
    onGoBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(sessionItems) {
        viewModel.onAction(WorkoutSessionAction.OnSessionSetup(sessionItems))
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
        ) {
            Spacer(modifier = Modifier.height(NormalSpacing))
            Text(
                text = stringResource(
                    Res.string.round_progress,
                    2,
                    6
                ),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            TabataBar(progress = 0.66f, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(LargeSpacing))
            TabataWorkoutTimer(
                progress = 0.66f,
                timeRemaining = "00:25",
                currentWorkout = "Work",
                nextWorkout = "Rest",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = LargeSpacing)
            )
        }
    }
}

@Preview
@Composable
private fun WorkoutSessionScreenPreview() {
    TabataTimerTheme {
        WorkoutSessionScreen(
            state = WorkoutSessionState(),
            onAction = {}
        )
    }
}