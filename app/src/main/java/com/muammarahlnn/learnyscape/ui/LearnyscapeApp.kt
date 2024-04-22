package com.muammarahlnn.learnyscape.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeBackground
import com.muammarahlnn.learnyscape.core.ui.util.ChangeStatusBarColor
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HOME_NAVIGATOR_ROUTE
import com.muammarahlnn.learnyscape.feature.login.navigation.LOGIN_ROUTE
import com.muammarahlnn.learnyscape.navigation.LearnyscapeNavHost


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeApp, 19/07/2023 22.11 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeApp(
    appState: LearnyscapeAppState,
    isUserLoggedIn: Boolean,
) {
    LearnyscapeBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            val startDestination = if (isUserLoggedIn) {
                HOME_NAVIGATOR_ROUTE
            } else {
                LOGIN_ROUTE
            }

            ChangeStatusBarColor(statusBarColor = appState.currentStatusBarColor)
            val user by appState.user.collectAsStateWithLifecycle()
            CompositionLocalProvider(LocalUserModel provides user) {
                LearnyscapeNavHost(
                    appState = appState,
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                )
            }
        }
    }
}