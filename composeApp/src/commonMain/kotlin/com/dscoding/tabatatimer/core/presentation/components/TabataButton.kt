package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun TabataButton(
    text: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
    isSecondaryButton: Boolean = false,
    iconImageVector: ImageVector? = null,
    modifier: Modifier = Modifier,
) {

    val borderColor = if(isSecondaryButton) {
        color
    } else {
        Transparent
    }

    val containerColor = if(isSecondaryButton) {
        MaterialTheme.colorScheme.background
    } else {
        color
    }

    val contentColor = if(isSecondaryButton) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .widthIn(max = 400.dp)
            .height(60.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = NormalSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NormalSpacing)
        ) {
            iconImageVector?.let { imageVector ->
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
private fun TabataButtonPreview() {
    TabataTimerTheme {
        TabataButton(
            iconImageVector = Icons.Default.PlayArrow,
            text = "Start Workout",
            onClick = {},
            modifier = Modifier
                .padding(NormalSpacing)
        )
    }
}

@Preview
@Composable
private fun TabataButtonSecondaryPreview() {
    TabataTimerTheme {
        TabataButton(
            iconImageVector = Icons.Default.PlayArrow,
            text = "Start Workout",
            isSecondaryButton = true,
            onClick = {},
            modifier = Modifier
                .padding(NormalSpacing)
        )
    }
}