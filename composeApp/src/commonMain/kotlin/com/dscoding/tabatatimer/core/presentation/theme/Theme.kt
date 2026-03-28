package com.dscoding.tabatatimer.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AppOrange,
    onPrimary = AppWhite,
    primaryContainer = AppDarkGrey,
    onPrimaryContainer = AppWhite,
    background = AppBlack,
    onBackground = AppWhite,
    secondary = AppYellow,
    tertiary = AppBlue,
    onSurfaceVariant = AppLightGrey,
)

@Composable
fun TabataTimerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}