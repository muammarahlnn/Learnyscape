package com.muammarahlnn.learnyscape.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeBackground
import com.muammarahlnn.learnyscape.navigation.LearnyscapeNavHost


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeApp, 19/07/2023 22.11 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearnyscapeApp(
    appState: LearnyscapeAppState = rememberLearnyscapeAppState(),
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor by animateColorAsState(
        targetValue = appState.currentStatusBarColor,
        label = "Status bar color"
    )
    LaunchedEffect(systemUiController, statusBarColor) {
        systemUiController.setStatusBarColor(
            color = statusBarColor
        )
    }
    
    LearnyscapeBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            LearnyscapeNavHost(
                appState = appState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        }
    }
}