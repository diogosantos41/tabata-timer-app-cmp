package com.dscoding.tabatatimer.core.presentation.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dscoding.tabatatimer.core.presentation.models.WorkoutType

@Composable
fun WorkoutType.color(): Color {
    return when (this) {
        WorkoutType.Starting -> MaterialTheme.colorScheme.tertiary
        WorkoutType.Work -> MaterialTheme.colorScheme.primary
        WorkoutType.Rest -> MaterialTheme.colorScheme.secondary
    }
}