package com.muammarahlnn.learnyscape.feature.login.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.login.LoginController
import com.muammarahlnn.learnyscape.feature.login.LoginRoute

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LoginNavigation, 22/04/2024 22.08
 */
const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin(popUpToRoute: String) {
    this.navigate(LOGIN_ROUTE) {
        popUpTo(popUpToRoute) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.loginScreen(
    navigateToHomeNavigator: () -> Unit,
) {
    composable(route = LOGIN_ROUTE) {
        LoginRoute(
            controller = LoginController(
                scope = rememberCoroutineScope(),
                navigateToHomeNavigator = navigateToHomeNavigator,
            )
        )
    }
}