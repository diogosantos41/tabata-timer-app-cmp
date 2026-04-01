package com.dscoding.tabatatimer.workout.presentation.workout_session.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun TabataCircularBar(
    progress: Float,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    modifier: Modifier = Modifier
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    CircularProgressIndicator(
        progress = { animatedProgress },
        color = progressColor,
        trackColor = containerColor,
        strokeCap = StrokeCap.Round,
        strokeWidth = 12.dp,
        gapSize = (-10).dp,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TabataCircularBarPreview() {
    TabataTimerTheme {
        Box(modifier = Modifier.padding(NormalSpacing), contentAlignment = Alignment.Center) {
            TabataCircularBar(
                progress = 0.66f,
                modifier = Modifier.size(300.dp)
            )
        }
    }
}