package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import org.jetbrains.compose.resources.stringResource
import tabatatimer.composeapp.generated.resources.Res
import tabatatimer.composeapp.generated.resources.rest_time
import tabatatimer.composeapp.generated.resources.total
import tabatatimer.composeapp.generated.resources.work_time

@Composable
fun WorkoutSummary(
    worktimeDisplayText: String,
    restTimeDisplayText: String,
    total: String,
    workWeight: Float,
    restWeight: Float,
    modifier: Modifier = Modifier,

    ) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(
            width = ContainerBorderWidth,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize),
        modifier = modifier
            .widthIn(max = 400.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WorkoutTimeInfo(
                    displayTime = worktimeDisplayText,
                    description = stringResource(Res.string.work_time),
                    color = MaterialTheme.colorScheme.primary
                )
                WorkoutTimeInfo(
                    displayTime = restTimeDisplayText,
                    description = stringResource(Res.string.rest_time),
                    color = MaterialTheme.colorScheme.secondary
                )
                WorkoutTimeInfo(
                    displayTime = total,
                    description = stringResource(Res.string.total),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            WorkRestBar(
                workWeight = workWeight,
                restWeight = restWeight
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun WorkoutSummaryPreview() {
    TabataTimerTheme {
        Box(modifier = Modifier.padding(15.dp)) {
            WorkoutSummary(
                worktimeDisplayText = "02:30",
                restTimeDisplayText = "00:40",
                total = "03:20",
                workWeight = 0.66f,
                restWeight = 0.33f,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
