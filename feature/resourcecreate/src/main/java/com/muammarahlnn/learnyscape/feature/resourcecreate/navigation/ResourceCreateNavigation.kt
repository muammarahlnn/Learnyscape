package com.muammarahlnn.learnyscape.feature.resourcecreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateRoute

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateNavigation, 18/12/2023 02.42
 */
const val RESOURCE_CREATE_ROUTE = "resource_create_route"

fun NavController.navigateToResourceCreate(navOptions: NavOptions? = null) {
    this.navigate(RESOURCE_CREATE_ROUTE, navOptions)
}

fun NavGraphBuilder.resourceCreateScreen(
    onCloseClick: () -> Unit,
    onCameraClick: () -> Unit,
) {
    composable(route = RESOURCE_CREATE_ROUTE) {
        ResourceCreateRoute(
            onCloseClick = onCloseClick,
            onCameraClick = onCameraClick,
        )
    }
}