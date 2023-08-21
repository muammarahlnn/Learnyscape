package com.muammarahlnn.learnyscape.feature.resourcedetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsNavigation, 18/08/2023 01.19 by Muammar Ahlan Abimanyu
 */

const val RESOURCE_DETAILS_ROUTE = "resource_details_route"

fun NavController.navigateToResourceDetails(navOptions: NavOptions? = null) {
    this.navigate(RESOURCE_DETAILS_ROUTE, navOptions)
}

fun NavGraphBuilder.resourceDetailsScreen(
    onBackClick: () -> Unit,
) {
    composable(route = RESOURCE_DETAILS_ROUTE) {
        ResourceDetailsRoute(
            onBackClick = onBackClick,
        )
    }
}