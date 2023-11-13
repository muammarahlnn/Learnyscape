package com.muammarahlnn.learnyscape.core.ui.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.muammarahlnn.learnyscape.core.model.data.UserModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UiHelper, 10/10/2023 01.48 by Muammar Ahlan Abimanyu
 */

val LocalUserModel = staticCompositionLocalOf { UserModel() }

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(
        label = "Shimmer infinite transition"
    )
    val startOffsetX by transition.animateFloat(
        initialValue = -1.2f * size.width.toFloat(),
        targetValue = 1.2f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1500)
        ),
        label = "Shimmer start offset X animation"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.onSecondary,
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.onSecondary,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { layoutCoordinates ->
        size = layoutCoordinates.size
    }
}