package com.muammarahlnn.learnyscape.feature.joinrequest.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestRoute

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestNavigation, 16/12/2023 04.34
 */

const val JOIN_REQUEST_ROUTE = "join_request_route" 

fun NavController.navigateToJoinRequest(navOptions: NavOptions? = null) {
    this.navigate(JOIN_REQUEST_ROUTE, navOptions)
}

fun NavGraphBuilder.joinRequestScreen(
    onBackClick: () -> Unit,
) {
    composable(route = JOIN_REQUEST_ROUTE) {
        JoinRequestRoute(onBackClick = onBackClick)
    }
}