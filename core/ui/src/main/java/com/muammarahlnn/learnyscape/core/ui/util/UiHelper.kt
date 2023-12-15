package com.muammarahlnn.learnyscape.core.ui.util

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.core.model.data.UserRole


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UiHelper, 10/10/2023 01.48 by Muammar Ahlan Abimanyu
 */

val LocalUserModel = staticCompositionLocalOf { UserModel() }

fun Modifier.shimmerEffect(
    shadowBrushWidth: Int = 500,
    axisYAngle: Float = 270f,
    effectDuration: Int = 1000,
): Modifier = composed {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 1f),
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
    )
    val transition = rememberInfiniteTransition(
        label = "Shimmer infinite transition"
    )
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = (effectDuration + shadowBrushWidth).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = effectDuration,
                easing = LinearEasing
            ),
        ),
        label = "Shimmer loading animation",
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(
                x = translateAnimation - shadowBrushWidth,
                y = 0f,
            ),
            end = Offset(
                x = translateAnimation,
                y = axisYAngle,
            ),
        )
    )
}

@Composable
fun ChangeStatusBarColor(statusBarColor: Color) {
    val systemUiController = rememberSystemUiController()
    val animatedStatusBarColor by animateColorAsState(
        targetValue = statusBarColor,
        label = "Status bar color"
    )
    LaunchedEffect(systemUiController, animatedStatusBarColor) {
        systemUiController.setStatusBarColor(
            color = animatedStatusBarColor
        )
    }
}

@Composable
fun LecturerOnlyComposable(content: @Composable () -> Unit) {
    if (isLecturer(LocalUserModel.current.role)) {
        content()
    }
}

@Composable
fun StudentOnlyComposable(content: @Composable () -> Unit) {
    if (isStudent(LocalUserModel.current.role)) {
        content()
    }
}

fun executeForLecturer(user: UserModel, execute: () -> Unit) {
    if (isLecturer(user.role)) execute()
}

fun executeForStudent(user: UserModel, execute: () -> Unit) {
    if (isStudent(user.role)) execute()
}

fun isLecturer(userRole: UserRole): Boolean = userRole == UserRole.LECTURER

fun isStudent(userRole: UserRole): Boolean = userRole == UserRole.STUDENT