package com.muammarahlnn.learnyscape.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeBackground
import com.muammarahlnn.learnyscape.core.domain.home.GetLoggedInUserUseCase
import com.muammarahlnn.learnyscape.core.ui.util.ChangeStatusBarColor
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.feature.login.LoginRoute
import com.muammarahlnn.learnyscape.navigation.LearnyscapeNavHost


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeApp, 19/07/2023 22.11 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeApp(
    getLoggedInUserUseCase: GetLoggedInUserUseCase,
    appState: LearnyscapeAppState = rememberLearnyscapeAppState(
        getLoggedInUserUseCase = getLoggedInUserUseCase,
    ),
    viewModel: LearnyscapeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.checkIfUserLoggedIn()
    }
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle()

    LearnyscapeBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            if (isUserLoggedIn) {
                ChangeStatusBarColor(statusBarColor = appState.currentStatusBarColor)

                val user by appState.user.collectAsStateWithLifecycle()
                CompositionLocalProvider(LocalUserModel provides user) {
                    LearnyscapeNavHost(
                        appState = appState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .consumeWindowInsets(padding)
                    )
                }
            } else {
                LoginRoute(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
        }
    }
}