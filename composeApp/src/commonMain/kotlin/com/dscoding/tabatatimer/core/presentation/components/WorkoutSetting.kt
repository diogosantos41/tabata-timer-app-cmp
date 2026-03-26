package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.FireExtinguisher
import androidx.compose.material.icons.filled.FireTruck
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Timer3
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.TestOnly
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme
import kotlin.invoke

@Composable
fun WorkoutSetting(
    icon: ImageVector,
    label: String,
    value: String,
    onSettingIncrease: () -> Unit,
    onSettingDecrease: () -> Unit,
    modifier: Modifier = Modifier
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            ActionButton(text = "-", onClick = onSettingDecrease)
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            ActionButton(text = "+", onClick = onSettingIncrease)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun WorkoutSettingPreview() {
    TabataTimerTheme {
        Box(modifier = Modifier.padding(15.dp)) {
            WorkoutSetting(
                icon = Icons.Default.AccessTime,
                label = "Work Time",
                value = "20s",
                onSettingIncrease = {},
                onSettingDecrease = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
