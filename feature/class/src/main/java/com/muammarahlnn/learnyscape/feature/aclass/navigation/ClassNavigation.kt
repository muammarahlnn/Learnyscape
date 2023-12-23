package com.muammarahlnn.learnyscape.feature.aclass.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.aclass.ClassRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigation, 04/08/2023 00.08 by Muammar Ahlan Abimanyu
 */

const val CLASS_ROUTE = "class_route"

fun NavController.navigateToClass(navOptions: NavOptions? = null) {
    this.navigate(CLASS_ROUTE, navOptions)
}

fun NavGraphBuilder.classScreen(
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    onCreateNewAnnouncementClick: () -> Unit,
    onPostClick: (Int) -> Unit,
) {
    composable(route = CLASS_ROUTE) {
        ClassRoute(
            onBackClick = onBackClick,
            onJoinRequestsClick = onJoinRequestsClick,
            onCreateNewAnnouncementClick = onCreateNewAnnouncementClick,
            onPostClick = onPostClick,
        )
    }
}