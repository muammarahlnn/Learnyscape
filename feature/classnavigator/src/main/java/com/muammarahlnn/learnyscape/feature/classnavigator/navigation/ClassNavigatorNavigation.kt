package com.muammarahlnn.learnyscape.feature.classnavigator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.classnavigator.ClassNavigatorRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigatorNavigation, 15/09/2023 19.45 by Muammar Ahlan Abimanyu
 */

const val CLASS_NAVIGATOR_ROUTE = "class_navigator_route"

fun NavController.navigateToClassNavigator() {
    this.navigate(CLASS_NAVIGATOR_ROUTE)
}

fun NavGraphBuilder.classNavigator(
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    onCreateNewResourceClick: (Int) -> Unit,
    onResourceClassClick: (Int) -> Unit,
) {
    composable(route = CLASS_NAVIGATOR_ROUTE) {
        ClassNavigatorRoute(
            onBackClick = onBackClick,
            onJoinRequestsClick = onJoinRequestsClick,
            onCreateNewResourceClick = onCreateNewResourceClick,
            onResourceClassClick = onResourceClassClick,
        )
    }
}