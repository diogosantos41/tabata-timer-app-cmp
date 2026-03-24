package com.dscoding.tabatatimer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.dscoding.tabatatimer.presentation.navigation.NavigationRoot

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationRoot(
            navController = rememberNavController()
        )
    }
}