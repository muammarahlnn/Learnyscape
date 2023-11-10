package com.muammarahlnn.learnyscape.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.home.HomeRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigation, 20/07/2023 19.44 by Muammar Ahlan Abimanyu
 */

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onClassClick: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(
            onClassClick = onClassClick,
        )
    }
}