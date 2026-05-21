package com.dscoding.tabatatimer.workout.presentation.workout_finished.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.workout.presentation.workout_finished.models.WorkoutSummary
import org.jetbrains.compose.resources.stringResource
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.workout_summary_calories
import tabatatimer.composeapp.generated.resources.workout_summary_duration
import tabatatimer.composeapp.generated.resources.workout_summary_rounds

@Composable
fun WorkoutSummarySection(
    summary: WorkoutSummary,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
    ) {
        WorkoutSummaryStat(
            value = summary.formattedDuration,
            label = stringResource(Res.string.workout_summary_duration),
            modifier = Modifier.weight(1f),
        )
        WorkoutSummaryStat(
            value = summary.rounds.toString(),
            label = stringResource(Res.string.workout_summary_rounds),
            modifier = Modifier.weight(1f),
        )
        WorkoutSummaryStat(
            value = summary.calories.toString(),
            label = stringResource(Res.string.workout_summary_calories),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun WorkoutSummaryStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun WorkoutSummarySectionPreview() {
    TabataTimerTheme {
        Box(modifier = Modifier.padding(NormalSpacing)) {
            WorkoutSummarySection(
                summary = WorkoutSummary(
                    formattedDuration = "10:00",
                    rounds = 5,
                    calories = 200,
                ),
            )
        }
    }
}
