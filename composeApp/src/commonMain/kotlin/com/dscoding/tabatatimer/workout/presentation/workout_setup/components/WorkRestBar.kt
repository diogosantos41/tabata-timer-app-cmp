package com.dscoding.tabatatimer.workout.presentation.workout_setup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dscoding.tabatatimer.core.presentation.components.TabataBar
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import org.jetbrains.compose.resources.stringResource
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.rest
import tabatatimer.composeapp.generated.resources.work

@Composable
fun WorkRestBar(
    workWeight: Float,
    workColor: Color = MaterialTheme.colorScheme.primary,
    restColor: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {

    Column {
        TabataBar(
            progress = workWeight,
            progressColor = workColor,
            containerColor = restColor,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(NormalSpacing))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BarIndicatorLabel(
                text = stringResource(Res.string.work),
                color = workColor,
            )
            BarIndicatorLabel(
                text = stringResource(Res.string.rest),
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
        Box(modifier = Modifier.padding(NormalSpacing)) {
            WorkRestBar(
                workWeight = 0.8f,
            )
        }
    }
}