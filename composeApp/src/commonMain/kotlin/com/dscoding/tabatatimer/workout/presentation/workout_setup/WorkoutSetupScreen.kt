package com.dscoding.tabatatimer.workout.presentation.workout_setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HdrStrong
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SettingsPower
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dscoding.tabatatimer.core.presentation.components.TabataChip
import com.dscoding.tabatatimer.core.presentation.components.TabataPrimaryButton
import com.dscoding.tabatatimer.core.presentation.components.TabataSectionHeader
import com.dscoding.tabatatimer.core.presentation.components.WorkoutSetting
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
                .padding(horizontal = 12.dp),
        ) {
            TabataSectionHeader(text = "Quick Presets")
            Spacer(modifier = Modifier.height(14.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
            Spacer(modifier = Modifier.height(30.dp))
            TabataSectionHeader(text = "Custom Settings")
            Spacer(modifier = Modifier.height(14.dp))
            WorkoutSetting(
                icon = Icons.Default.Handshake,
                label = "Work Time",
                value = "20s",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            WorkoutSetting(
                icon = Icons.Default.AccessTime,
                label = "Rest Time",
                value = "10s",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            WorkoutSetting(
                icon = Icons.Default.Refresh,
                label = "Rounds",
                value = "8",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            TabataPrimaryButton(
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