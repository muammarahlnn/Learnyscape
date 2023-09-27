package com.muammarahlnn.learnyscape.feature.resourcedetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsNavigation, 18/08/2023 01.19 by Muammar Ahlan Abimanyu
 */

private const val RESOURCE_DETAILS_ROUTE = "resource_details_route"
private const val RESOURCE_TYPE_ORDINAL_ARG = "resource_type_ordinal"
const val RESOURCE_DETAILS_ROUTE_WITH_ARGS =
    "$RESOURCE_DETAILS_ROUTE/{$RESOURCE_TYPE_ORDINAL_ARG}"

internal class ResourceDetailsArgs(
    val resourceTypeOrdinal: Int,
) {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        resourceTypeOrdinal = checkNotNull(savedStateHandle[RESOURCE_TYPE_ORDINAL_ARG])
    )
}

fun NavController.navigateToResourceDetails(
    resourceTypeOrdinal: Int,
) {
    this.navigate("$RESOURCE_DETAILS_ROUTE/$resourceTypeOrdinal") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.resourceDetailsScreen(
    onConfirmStartQuizDialog: (Int, String, Int) -> Unit,
    onCameraActionClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = RESOURCE_DETAILS_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(RESOURCE_TYPE_ORDINAL_ARG) {
                type = NavType.IntType
            },
        ),
    ) {
        ResourceDetailsRoute(
            onConfirmStartQuizDialog = onConfirmStartQuizDialog,
            onCameraActionClick = onCameraActionClick,
            onBackClick = onBackClick,
        )
    }
}