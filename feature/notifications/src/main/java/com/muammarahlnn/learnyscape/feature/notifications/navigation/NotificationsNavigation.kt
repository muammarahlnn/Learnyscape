package com.muammarahlnn.learnyscape.feature.notifications.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NotificationsNavigation, 01/08/2023 13.19 by Muammar Ahlan Abimanyu
 */

const val notificationsRoute = "notifications_route"

fun NavController.navigateToNotifications(navOptions: NavOptions? = null) {
    this.navigate(notificationsRoute, navOptions)
}

fun NavGraphBuilder.notificationsScreen(
    onBackClick: () -> Unit,
) {
    composable(route = notificationsRoute) {
        NotificationsRoute(
            onBackClick = onBackClick,
        )
    }
}