package com.dscoding.tabatatimer.core.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun TabataChip(
    title: String,
    description: String,
    onClick: () -> Unit,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (isHighlighted) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
    val borderColor = if (isHighlighted) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
    }

    val textColor = if (isHighlighted) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    Box(
        modifier = modifier
            .then(
                if (isHighlighted) {
                    Modifier.shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(Dimens.ContainerRoundedCornerShapeSize)
                    )
                } else Modifier
            )
            .clip(shape = RoundedCornerShape(Dimens.ContainerRoundedCornerShapeSize))
            .border(
                width = 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(Dimens.ContainerRoundedCornerShapeSize)
            )
            .background(containerColor)
            .clickable(onClick = onClick)
            .animateContentSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 14.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = textColor
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun TabataChipPreview() {
    TabataTimerTheme {
        TabataChip(
            title = "Classic",
            description = "20s / 10s / 8",
            onClick = {},
            isHighlighted = true,
        )
    }
}