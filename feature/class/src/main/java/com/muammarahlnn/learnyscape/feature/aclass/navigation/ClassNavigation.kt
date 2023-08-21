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
const val CLASS_GRAPH_ROUTE_PATTERN = "class_graph"
const val CLASS_ROUTE = "class_route"

fun NavController.navigateToClassGraph(navOptions: NavOptions? = null) {
    this.navigate(CLASS_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.classGraph(
    onPostClick: () -> Unit,
    onBackClick: () -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = CLASS_GRAPH_ROUTE_PATTERN,
        startDestination = CLASS_ROUTE,
    ) {
        composable(route = CLASS_ROUTE) {
            ClassRoute(
                onPostClick = onPostClick,
                onBackClick = onBackClick,
            )
        }
        nestedGraph()
    }
}