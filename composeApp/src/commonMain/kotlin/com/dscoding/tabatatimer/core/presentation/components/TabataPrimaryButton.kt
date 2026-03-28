package com.dscoding.tabatatimer.core.presentation.components

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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.TabataTimerTheme

@Composable
fun TabataPrimaryButton(
    text: String,
    onClick: () -> Unit,
    iconImageVector: ImageVector? = null,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize),
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

@Preview(showBackground = true)
@Composable
private fun TabataPrimaryButtonPreview() {
    TabataTimerTheme {
        TabataPrimaryButton(
            iconImageVector = Icons.Default.PlayArrow,
            text = "Start Workout",
            onClick = {},
            modifier = Modifier
                .padding(NormalSpacing)
        )
    }
}