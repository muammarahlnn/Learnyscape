package com.muammarahlnn.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Background, 24/07/2023 12.10 by Muammar Ahlan Abimanyu
 */

@Immutable
data class BackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified
)

val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }