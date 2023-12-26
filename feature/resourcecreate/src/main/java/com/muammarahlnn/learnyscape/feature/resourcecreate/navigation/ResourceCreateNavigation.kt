package com.muammarahlnn.learnyscape.feature.resourcecreate.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateRoute

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateNavigation, 18/12/2023 02.42
 */
private const val RESOURCE_CREATE_ROUTE = "resource_create_route"
private const val RESOURCE_TYPE_ORDINAL_ARG = "resource_type_ordinal"
private const val RESOURCE_CREATE_ROUTE_WITH_ARGS =
    "$RESOURCE_CREATE_ROUTE/{$RESOURCE_TYPE_ORDINAL_ARG}"

internal class ResourceCreateArgs(
    val resourceTypeOrdinal: Int,
) {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        resourceTypeOrdinal = checkNotNull(savedStateHandle[RESOURCE_TYPE_ORDINAL_ARG])
    )
}

fun NavController.navigateToResourceCreate(
    resourceTypeOrdinal: Int,
) {
    this.navigate("$RESOURCE_CREATE_ROUTE/$resourceTypeOrdinal") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.resourceCreateScreen(
    onCloseClick: () -> Unit,
    onCameraClick: () -> Unit,
) {
    composable(
        route = RESOURCE_CREATE_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(RESOURCE_TYPE_ORDINAL_ARG) {
                type = NavType.IntType
            }
        )
    ) {
        ResourceCreateRoute(
            onCloseClick = onCloseClick,
            onCameraClick = onCameraClick,
        )
    }
}