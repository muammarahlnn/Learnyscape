package com.muammarahlnn.learnyscape.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.ui.home.HomeNavigatorRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigatorNavigation, 14/09/2023 21.26 by Muammar Ahlan Abimanyu
 */

const val HOME_NAVIGATOR_ROUTE = "home_navigator_route"

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