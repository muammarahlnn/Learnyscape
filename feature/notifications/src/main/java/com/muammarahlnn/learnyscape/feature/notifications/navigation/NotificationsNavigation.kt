package com.muammarahlnn.learnyscape.feature.notifications.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsController
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NotificationsNavigation, 01/08/2023 13.19 by Muammar Ahlan Abimanyu
 */

const val NOTIFICATIONS_ROUTE = "notifications_route"

fun NavController.navigateToNotifications(navOptions: NavOptions? = null) {
    this.navigate(NOTIFICATIONS_ROUTE, navOptions)
}

fun NavGraphBuilder.notificationsScreen(
    navigateBack: () -> Unit,
    navigateToResourceDetails: (String, String, Int) -> Unit,
) {
    composable(route = NOTIFICATIONS_ROUTE) {
        NotificationsRoute(
            controller = NotificationsController(
                scope = rememberCoroutineScope(),
                navigateBack = navigateBack,
                navigateToResourceDetails = navigateToResourceDetails,
            )
        )
    }
}