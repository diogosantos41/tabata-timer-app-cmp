package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
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
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(min = 48.dp)
                    .padding(horizontal = 4.dp)
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
