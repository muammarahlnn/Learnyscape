package com.muammarahlnn.learnyscape.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.ui.aclass.ClassNavigatorRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigatorNavigation, 15/09/2023 11.09 by Muammar Ahlan Abimanyu
 */

const val CLASS_NAVIGATOR_ROUTE = "class_navigator_route"

fun NavController.navigateToClassNavigator() {
    this.navigate(CLASS_NAVIGATOR_ROUTE)
}

fun NavGraphBuilder.classNavigator(
    onResourceClassClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = CLASS_NAVIGATOR_ROUTE) {
        ClassNavigatorRoute(
            onResourceClassClick = onResourceClassClick,
            onBackClick = onBackClick,
        )
    }
}