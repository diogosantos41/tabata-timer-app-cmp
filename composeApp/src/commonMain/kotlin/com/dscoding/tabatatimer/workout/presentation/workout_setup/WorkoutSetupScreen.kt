package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataChip
import com.dscoding.tabatatimer.core.presentation.components.TabataPrimaryButton
import com.dscoding.tabatatimer.core.presentation.components.TabataSectionHeader
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSetting
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSummary
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.core.presentation.utils.ObserveAsEvents
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.WorkoutPreset
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.custom_settings
import tabatatimer.composeapp.generated.resources.quick_presets
import tabatatimer.composeapp.generated.resources.rest
import tabatatimer.composeapp.generated.resources.rounds
import tabatatimer.composeapp.generated.resources.start_workout
import tabatatimer.composeapp.generated.resources.work
import tabatatimer.composeapp.generated.resources.workout_summary

@Composable
fun WorkoutSetupRoot(
    viewModel: WorkoutSetupViewModel = koinViewModel(),
    onStartWorkout: (work: Int, rest: Int, rounds: Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is WorkoutSetupEvent.OnStartWorkout -> onStartWorkout(
                event.work,
                event.rest,
                event.rounds
            )
        }
    }

    WorkoutSetupScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun WorkoutSetupScreen(
    state: WorkoutSetupState,
    onAction: (WorkoutSetupAction) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TabataSectionHeader(text = stringResource(Res.string.quick_presets))
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WorkoutPreset.entries.forEach { preset ->
                    TabataChip(
                        title = preset.title.asString(),
                        description = preset.displayInfo,
                        onClick = { onAction(WorkoutSetupAction.OnPresetClick(preset)) },
                        isHighlighted = false,
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            TabataSectionHeader(text = stringResource(Res.string.custom_settings))
            Spacer(modifier = Modifier.height(16.dp))
            WorkoutSetting(
                icon = Icons.Default.Moving,
                label = stringResource(Res.string.work),
                value = state.selectedWorkTime?.displayTime ?: "",
                onSettingIncrease = { onAction(WorkoutSetupAction.OnWorkTimeChanged) },
                onSettingDecrease = { onAction(WorkoutSetupAction.OnWorkTimeChanged) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            WorkoutSetting(
                icon = Icons.Default.Schedule,
                label = stringResource(Res.string.rest),
                value = state.selectedRestTime?.displayTime ?: "",
                onSettingIncrease = { onAction(WorkoutSetupAction.OnRestTimeChanged) },
                onSettingDecrease = { onAction(WorkoutSetupAction.OnRestTimeChanged) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            WorkoutSetting(
                icon = Icons.Default.Sync,
                label = stringResource(Res.string.rounds),
                value = state.selectedRounds.toString(),
                onSettingIncrease = { onAction(WorkoutSetupAction.OnRoundsChanged) },
                onSettingDecrease = { onAction(WorkoutSetupAction.OnRoundsChanged) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            TabataSectionHeader(text = stringResource(Res.string.workout_summary))
            Spacer(modifier = Modifier.height(16.dp))
            WorkoutSummary(
                worktimeDisplayText = state.totalWorkTime,
                restTimeDisplayText = state.totalRestTime,
                total = state.totalWorkoutTime,
                workWeight = state.workTimeRatio,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            TabataPrimaryButton(
                iconImageVector = Icons.Default.PlayArrow,
                text = stringResource(Res.string.start_workout),
                onClick = { onAction(WorkoutSetupAction.OnStartWorkoutClick) },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
        }

    }
}

@Preview
@Composable
private fun Preview() {
    TabataTimerTheme {
        WorkoutSetupScreen(
            state = WorkoutSetupState(
                selectedWorkTime = TimeUi(
                    seconds = WorkoutPreset.Classic.work,
                ),
                selectedRestTime = TimeUi(
                    seconds = WorkoutPreset.Classic.rest,
                ),
                selectedRounds = WorkoutPreset.Classic.rounds,
            ),
            onAction = {}
        )
    }
}