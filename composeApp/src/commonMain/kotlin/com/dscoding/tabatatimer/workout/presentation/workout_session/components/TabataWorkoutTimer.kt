package com.dscoding.tabatatimer.workout.presentation.workout_session.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import org.jetbrains.compose.resources.stringResource
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.next_workout

@Composable
fun TabataWorkoutTimer(
    progress: Float,
    timeRemaining: String,
    currentWorkout: String,
    nextWorkout: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        TabataCircularBar(
            progress = progress,
            progressColor = color,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-16).dp)
        ) {
            Text(
                text = currentWorkout.uppercase(),
                color = color,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = timeRemaining,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(
                    Res.string.next_workout,
                    nextWorkout
                ).uppercase(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun TabataWorkoutTimerPreview() {
    TabataTimerTheme {
        TabataWorkoutTimer(
            progress = 0.66f,
            timeRemaining = "00:25",
            currentWorkout = "Work",
            nextWorkout = "Rest",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}