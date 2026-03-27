package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun WorkoutTimeInfo(
    displayTime: String,
    description: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = displayTime,
            style = MaterialTheme.typography.titleSmall,
            color = color,
        )
        Text(
            text = description.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun WorkoutTimeInfoPreview() {
    TabataTimerTheme {
        Box(modifier = Modifier.padding(12.dp)) {
            WorkoutTimeInfo(
                displayTime = "02:30",
                description = "Work Time",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}