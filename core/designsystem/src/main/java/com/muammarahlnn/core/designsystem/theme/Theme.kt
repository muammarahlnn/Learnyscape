package com.muammarahlnn.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Theme, 15/07/2023 13.22 by Muammar Ahlan Abimanyu
 */

val LightColorScheme = lightColorScheme(
    primary = Red,
    onPrimary = White,
    secondary = Gray,
    onSecondary = White,
    tertiary = Blue,
    onTertiary = White,
    background = WhiteSmoke,
    surface = WhiteSmoke,
)

@Composable
fun LearnyscapeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = LearnyscapeTypography,
        content = content
    )
}