package com.muammarahlnn.learnyscape.feature.aclass.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.muammarahlnn.learnyscape.feature.aclass.ClassRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigation, 04/08/2023 00.08 by Muammar Ahlan Abimanyu
 */
const val classGraphRoutePattern = "class_graph"
const val classRoute = "class_route"

fun NavController.navigateToClassGraph(navOptions: NavOptions? = null) {
    this.navigate(classGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.classGraph(
    onBackClick: () -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = classGraphRoutePattern,
        startDestination = classRoute,
    ) {
        composable(route = classRoute) {
            ClassRoute(
                onBackClick = onBackClick,
            )
        }
        nestedGraph()
    }
}