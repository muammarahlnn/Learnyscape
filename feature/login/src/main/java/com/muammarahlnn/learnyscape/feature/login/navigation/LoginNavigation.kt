package com.muammarahlnn.learnyscape.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.login.LoginRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginNavigation, 24/09/2023 01.28 by Muammar Ahlan Abimanyu
 */
const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin() {
    this.navigate(LOGIN_ROUTE)
}

fun NavGraphBuilder.loginScreen(
    onLoginButtonClick: () -> Unit,
) {
    composable(route = LOGIN_ROUTE) {
        LoginRoute(
            onLoginButtonClick = onLoginButtonClick,
        )
    }
}