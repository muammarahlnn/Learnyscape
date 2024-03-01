package com.muammarahlnn.learnyscape.feature.pendingrequest.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestController
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file PendingRequestNavigation, 10/10/2023 18.23 by Muammar Ahlan Abimanyu
 */

const val PENDING_REQUEST_ROUTE = "pending_request_route"

fun NavController.navigateToPendingRequest(navOptions: NavOptions? = null) {
    this.navigate(PENDING_REQUEST_ROUTE, navOptions)
}

fun NavGraphBuilder.pendingRequestScreen(
    navigateBack: () -> Unit,
) {
    composable(route = PENDING_REQUEST_ROUTE) {
        PendingRequestRoute(
            controller = PendingRequestController(
                scope = rememberCoroutineScope(),
                navigateBack = navigateBack,
            )
        )
    }
}