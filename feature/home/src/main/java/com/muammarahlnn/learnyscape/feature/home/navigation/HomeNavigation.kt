package com.muammarahlnn.learnyscape.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.muammarahlnn.learnyscape.feature.home.HomeRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigation, 20/07/2023 19.44 by Muammar Ahlan Abimanyu
 */

const val homeGraphRoutePattern = "home_graph"
const val homeRoute = "home_route"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(homeGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.homeGraph(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = homeGraphRoutePattern,
        startDestination = homeRoute
    ) {
        composable(route = homeRoute) {
            HomeRoute(
                onNotificationsClick = onNotificationsClick,
                onClassClick = onClassClick,
            )
        }
        nestedGraph()
    }
}