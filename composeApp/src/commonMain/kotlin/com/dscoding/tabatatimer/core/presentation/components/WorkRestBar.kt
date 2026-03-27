package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun WorkRestBar(
    workWeight: Float,
    restWeight: Float,
    workColor: Color = MaterialTheme.colorScheme.primary,
    restColor: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    val animatedWorkWeight by animateFloatAsState(
        targetValue = workWeight,
        animationSpec = tween(400),
        label = "workWeight"
    )

    val animatedRestWeight by animateFloatAsState(
        targetValue = restWeight,
        animationSpec = tween(400),
        label = "restWeight"
    )
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(50))
        ) {
            Box(
                modifier = Modifier
                    .weight(animatedWorkWeight.coerceAtLeast(0.001f))
                    .fillMaxHeight()
                    .background(workColor)
            )
            Box(
                modifier = Modifier
                    .weight(animatedRestWeight.coerceAtLeast(0.001f))
                    .fillMaxHeight()
                    .background(restColor)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BarIndicatorLabel(
                text = "Work",
                color = workColor,
            )
            BarIndicatorLabel(
                text = "Rest",
                color = restColor,
                isDotAtEnd = true
            )
        }
    }
}

@Preview
@Composable
private fun WorkRestBarPreview() {
    TabataTimerTheme {
        Box(modifier = Modifier.padding(15.dp)) {
            WorkRestBar(
                workWeight = 0.8f,
                restWeight = 0.2f
            )
        }
    }
}