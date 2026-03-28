package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.tabatatimer.core.presentation.components.TabataChip
import com.dscoding.tabatatimer.core.presentation.components.TabataPrimaryButton
import com.dscoding.tabatatimer.core.presentation.components.TabataSectionHeader
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSetting
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSummary
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.LargeSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.SmallSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.core.presentation.utils.ObserveAsEvents
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.SettingChange.Decrease
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.SettingChange.Increase
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.TimeUi
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.WorkoutPreset
import com.dscoding.tabatatimer.workout.presentation.workout_setup.models.defaultPreset
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
                event.workTime,
                event.restTime,
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
            TabataSectionHeader(text = stringResource(Res.string.quick_presets))
            Spacer(modifier = Modifier.height(NormalSpacing))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SmallSpacing),
                verticalArrangement = Arrangement.spacedBy(SmallSpacing)
            ) {
                WorkoutPreset.entries.forEach { preset ->
                    TabataChip(
                        title = preset.title.asString(),
                        description = preset.display,
                        onClick = { onAction(WorkoutSetupAction.OnPresetClick(preset)) },
                        isHighlighted = preset.matches(
                            workTime = state.selectedWorkTime.seconds,
                            restTime = state.selectedRestTime.seconds,
                            rounds = state.selectedRounds
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(LargeSpacing))
            TabataSectionHeader(text = stringResource(Res.string.custom_settings))
            Spacer(modifier = Modifier.height(NormalSpacing))
            WorkoutSetting(
                icon = Icons.Default.Moving,
                label = stringResource(Res.string.work),
                value = state.selectedWorkTime.displayTime,
                onSettingIncrease = { onAction(WorkoutSetupAction.OnWorkTimeChanged(Increase)) },
                onSettingDecrease = { onAction(WorkoutSetupAction.OnWorkTimeChanged(Decrease)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            WorkoutSetting(
                icon = Icons.Default.Schedule,
                label = stringResource(Res.string.rest),
                value = state.selectedRestTime.displayTime,
                onSettingIncrease = { onAction(WorkoutSetupAction.OnRestTimeChanged(Increase)) },
                onSettingDecrease = { onAction(WorkoutSetupAction.OnRestTimeChanged(Decrease)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            WorkoutSetting(
                icon = Icons.Default.Sync,
                label = stringResource(Res.string.rounds),
                value = state.selectedRounds.toString(),
                onSettingIncrease = { onAction(WorkoutSetupAction.OnRoundsChanged(Increase)) },
                onSettingDecrease = { onAction(WorkoutSetupAction.OnRoundsChanged(Decrease)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(LargeSpacing))
            TabataSectionHeader(text = stringResource(Res.string.workout_summary))
            Spacer(modifier = Modifier.height(NormalSpacing))
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
                    .padding(vertical = LargeSpacing)
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
                    seconds = defaultPreset.workTime,
                ),
                selectedRestTime = TimeUi(
                    seconds = defaultPreset.restTime,
                ),
                selectedRounds = defaultPreset.rounds,
            ),
            onAction = {}
        )
    }
}