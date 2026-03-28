package com.dscoding.tabatatimer.core.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.NormalSpacing
import com.dscoding.tabatatimer.core.presentation.theme.Dimens.SmallSpacing
import org.jetbrains.compose.resources.Font
import tabatatimer.composeapp.generated.resources.OpenSans_Bold
import tabatatimer.composeapp.generated.resources.OpenSans_Light
import tabatatimer.composeapp.generated.resources.OpenSans_Regular
import tabatatimer.composeapp.generated.resources.OpenSans_SemiBold
import tabatatimer.composeapp.generated.resources.Res

object Fonts {
    val OpenSans
        @Composable get() = FontFamily(
            Font(
                resource = Res.font.OpenSans_Light,
                weight = FontWeight.Light
            ),
            Font(
                resource = Res.font.OpenSans_Regular,
                weight = FontWeight.Normal
            ),
            Font(
                resource = Res.font.OpenSans_SemiBold,
                weight = FontWeight.SemiBold
            ),
            Font(
                resource = Res.font.OpenSans_Bold,
                weight = FontWeight.Bold
            ),
        )
}

val Typography: Typography
    @Composable get() = Typography(
        labelSmall = TextStyle(
            fontFamily = Fonts.OpenSans,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        ),
        labelLarge = TextStyle(
            fontFamily = Fonts.OpenSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = 1.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = Fonts.OpenSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = Fonts.OpenSans,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        )
    )

@Preview(showBackground = true)
@Composable
private fun TypographyPreview() {
    TabataTimerTheme {
        Column(
            modifier = Modifier.padding(SmallSpacing),
            verticalArrangement = Arrangement.spacedBy(NormalSpacing)
        ) {
            Text(text = "body medium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "title small", style = MaterialTheme.typography.titleSmall)
        }
    }
}
