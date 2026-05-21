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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataButton
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.LargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.SmallSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.xLargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.core.presentation.utils.ObserveAsEvents
import com.dscoding.tabatatimer.workout.presentation.workout_finished.components.WorkoutSummarySection
import com.dscoding.tabatatimer.workout.presentation.workout_finished.models.WorkoutSummary
import com.dscoding.tabatatimer.workout.presentation.workout_finished.utils.getRandomWorkoutCompletionMessage
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.go_again
import tabatatimer.composeapp.generated.resources.setup_new_workout
import tabatatimer.composeapp.generated.resources.workout_complete_1
import tabatatimer.composeapp.generated.resources.workout_complete_2

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
            Text(
                text = stringResource(Res.string.workout_complete_1).uppercase(),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(Res.string.workout_complete_2).uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            state.completionMessage?.let {
                Text(text = it.asString(), style = MaterialTheme.typography.titleSmall)
            }
            state.summary?.let { summary ->
                Spacer(modifier = Modifier.height(xLargeSpacing))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(NormalSpacing))
                WorkoutSummarySection(summary = summary)
                Spacer(modifier = Modifier.height(NormalSpacing))
                HorizontalDivider()
            }
            Spacer(modifier = Modifier.height(xLargeSpacing))
            TabataButton(
                text = stringResource(Res.string.go_again),
                onClick = { onAction(WorkoutFinishedAction.OnRestartWorkoutSession) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(NormalSpacing))
            TabataButton(
                text = stringResource(Res.string.setup_new_workout),
                isSecondaryButton = true,
                onClick = { onAction(WorkoutFinishedAction.OnGoBackToWorkoutSetup) },
                modifier = Modifier.fillMaxWidth(),

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
                summary = WorkoutSummary(
                    formattedDuration = "10:00",
                    rounds = 5,
                    calories = 200,
                ),
            ), onAction = {})
    }
}