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
    onNotificationsClick: () -> Unit,
    onClassClick: (String) -> Unit,
    onCameraActionClick: () -> Unit,
    onPendingClassRequestClick: () -> Unit,
    onChangePasswordButtonClick: () -> Unit,
) {
    composable(route = HOME_NAVIGATOR_ROUTE) {
        HomeNavigatorRoute(
            onNotificationsClick = onNotificationsClick,
            onClassClick = onClassClick,
            onCameraActionClick = onCameraActionClick,
            onPendingClassRequestClick = onPendingClassRequestClick,
            onChangePasswordButtonClick = onChangePasswordButtonClick,
        )
    }
}