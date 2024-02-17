package com.muammarahlnn.learnyscape.feature.homenavigator.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.homenavigator.HomeNavigatorRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigatorNavigation, 15/09/2023 19.49 by Muammar Ahlan Abimanyu
 */

const val HOME_NAVIGATOR_ROUTE = "home_navigator_route"

fun NavGraphBuilder.homeNavigator(
    navigateToNotifications: () -> Unit,
    navigateToClass: (String) -> Unit,
    navigateToCamera: () -> Unit,
    navigateToPendingRequestClass: () -> Unit,
    navigateToChangePassword: () -> Unit,
) {
    composable(route = HOME_NAVIGATOR_ROUTE) {
        HomeNavigatorRoute(
            navigateToNotifications = navigateToNotifications,
            navigateToClass = navigateToClass,
            navigateToCamera = navigateToCamera,
            navigateToPendingRequestClass = navigateToPendingRequestClass,
            navigateToChangePassword = navigateToChangePassword,
        )
    }
}