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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dscoding.tabatatimer.core.presentation.components.TabataChip
import com.dscoding.tabatatimer.core.presentation.components.TabataPrimaryButton
import com.dscoding.tabatatimer.core.presentation.components.TabataSectionHeader
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSetting
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSummary
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun WorkoutSetupRoot(
    viewModel: WorkoutSetupViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
            TabataSectionHeader(text = "Quick Presets")
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabataChip(
                    title = "Classic",
                    description = "20s / 10s / 8",
                    onClick = {},
                    isHighlighted = true,
                )
                TabataChip(
                    title = "Endurance",
                    description = "40s / 20s / 8",
                    onClick = {},
                    isHighlighted = false,
                )
                TabataChip(
                    title = "Quick",
                    description = "10s / 5s / 4",
                    onClick = {},
                    isHighlighted = false,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            TabataSectionHeader(text = "Custom Settings")
            Spacer(modifier = Modifier.height(16.dp))
            WorkoutSetting(
                icon = Icons.Default.Moving,
                label = "Work",
                value = "20s",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            WorkoutSetting(
                icon = Icons.Default.Schedule,
                label = "Rest",
                value = "10s",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            WorkoutSetting(
                icon = Icons.Default.Sync,
                label = "Rounds",
                value = "8",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            TabataSectionHeader(text = "Workout Summary")
            Spacer(modifier = Modifier.height(16.dp))
            WorkoutSummary(
                worktimeDisplayText = "02:30",
                restTimeDisplayText = "00:40",
                total = "03:20",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            TabataPrimaryButton(
                iconImageVector = Icons.Default.PlayArrow,
                text = "Start Workout",
                onClick = {},
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
            state = WorkoutSetupState(),
            onAction = {}
        )
    }
}