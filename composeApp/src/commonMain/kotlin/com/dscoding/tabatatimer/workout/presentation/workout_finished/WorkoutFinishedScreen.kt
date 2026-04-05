package com.dscoding.tabatatimer.workout.presentation.workout_finished

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataPrimaryButton
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.LargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.core.presentation.utils.ObserveAsEvents
import com.dscoding.tabatatimer.workout.presentation.workout_finished.utils.getRandomWorkoutCompletionMessage
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.go_again
import tabatatimer.composeapp.generated.resources.setup_new_workout

@Composable
fun WorkoutFinishedRoot(
    viewModel: WorkoutFinishedViewModel = koinViewModel(),
    onRestartWorkoutSession: () -> Unit,
    onGoBackToWorkoutSetup: () -> Unit,

    ) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            WorkoutFinishedEvent.GoBackToWorkoutSetup -> onGoBackToWorkoutSetup()
        }
    }

    WorkoutFinishedScreen(
        state = state, onAction = { action ->
            when (action) {
                is WorkoutFinishedAction.OnRestartWorkoutSession -> onRestartWorkoutSession()
                else -> Unit
            }
            viewModel.onAction(action)
        })
}

@Composable
fun WorkoutFinishedScreen(
    state: WorkoutFinishedState,
    onAction: (WorkoutFinishedAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = NormalSpacing)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.completionMessage?.let {
                Text(text = it.asString(), style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(modifier = Modifier.height(LargeSpacing))
            TabataPrimaryButton(
                text = stringResource(Res.string.go_again),
                onClick = { onAction(WorkoutFinishedAction.OnRestartWorkoutSession) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(NormalSpacing))
            TabataPrimaryButton(
                text = stringResource(Res.string.setup_new_workout),
                onClick = { onAction(WorkoutFinishedAction.OnGoBackToWorkoutSetup) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TabataTimerTheme {
        WorkoutFinishedScreen(
            state = WorkoutFinishedState(
                completionMessage = getRandomWorkoutCompletionMessage(),
            ), onAction = {})
    }
}