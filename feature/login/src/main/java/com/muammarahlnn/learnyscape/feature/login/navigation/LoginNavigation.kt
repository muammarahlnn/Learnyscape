package com.muammarahlnn.learnyscape.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginNavigation, 24/09/2023 01.28 by Muammar Ahlan Abimanyu
 */
const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(LOGIN_ROUTE, navOptions)
}