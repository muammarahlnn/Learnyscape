package com.muammarahlnn.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Theme, 15/07/2023 13.22 by Muammar Ahlan Abimanyu
 */

val LightColorScheme = lightColorScheme(
    primary = Red,
    onPrimary = White,
    primaryContainer = LightRed,
    secondary = Gray,
    onSecondary = White,
    tertiary = Blue,
    onTertiary = White,
    background = WhiteSmoke,
    onBackground = Black,
    surface = WhiteSmoke,
    onSurface = Gray,
    onSurfaceVariant = LightGray,
)

@Composable
fun LearnyscapeTheme(content: @Composable () -> Unit) {
    val defaultBackgroundTheme = BackgroundTheme(
        color = LightColorScheme.surface,
        tonalElevation = 0.dp
    )
    CompositionLocalProvider(
        LocalBackgroundTheme provides defaultBackgroundTheme
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = LearnyscapeTypography,
            content = content
        )
    }
}