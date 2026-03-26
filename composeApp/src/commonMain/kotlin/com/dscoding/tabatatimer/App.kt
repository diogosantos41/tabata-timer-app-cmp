package com.dscoding.tabatatimer

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import com.dscoding.tabatatimer.workout.presentation.navigation.NavigationRoot

@Composable
@Preview
fun App() {
    TabataTimerTheme {
        NavigationRoot(
            navController = rememberNavController()
        )
    }
}