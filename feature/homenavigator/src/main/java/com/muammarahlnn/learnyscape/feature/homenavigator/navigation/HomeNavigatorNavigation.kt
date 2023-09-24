package com.muammarahlnn.learnyscape.feature.homenavigator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.homenavigator.HomeNavigatorRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigatorNavigation, 15/09/2023 19.49 by Muammar Ahlan Abimanyu
 */

const val HOME_NAVIGATOR_ROUTE = "home_navigator_route"

fun NavController.navigateToHomeNavigator(navOptions: NavOptions? = null) {
    this.navigate(HOME_NAVIGATOR_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavigator(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
) {
    composable(route = HOME_NAVIGATOR_ROUTE) {
        HomeNavigatorRoute(
            onNotificationsClick = onNotificationsClick,
            onClassClick = onClassClick,
        )
    }
}