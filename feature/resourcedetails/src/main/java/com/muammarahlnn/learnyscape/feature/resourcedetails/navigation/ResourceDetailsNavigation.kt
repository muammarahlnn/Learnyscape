package com.muammarahlnn.learnyscape.feature.resourcedetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.core.common.decoder.StringCodec
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsNavigation, 18/08/2023 01.19 by Muammar Ahlan Abimanyu
 */

private const val RESOURCE_DETAILS_ROUTE = "resource_details_route"
private const val RESOURCE_TYPE_ARG = "resource_type"
const val RESOURCE_DETAILS_ROUTE_WITH_ARGS =
    "$RESOURCE_DETAILS_ROUTE/{$RESOURCE_TYPE_ARG}"

internal class ResourceDetailsArgs(
    val resourceType: String,
) {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        resourceType = StringCodec.decode(checkNotNull(savedStateHandle[RESOURCE_TYPE_ARG]))
    )
}

fun NavController.navigateToResourceDetails(
    resourceType: String,
) {
    val encodedResourceType = StringCodec.encode(resourceType)
    this.navigate("$RESOURCE_DETAILS_ROUTE/$encodedResourceType") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.resourceDetailsScreen(
    onConfirmStartQuizDialog: (String, Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = RESOURCE_DETAILS_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(RESOURCE_TYPE_ARG) {
                type = NavType.StringType
            },
        ),
    ) {
        ResourceDetailsRoute(
            onConfirmStartQuizDialog = onConfirmStartQuizDialog,
            onBackClick = onBackClick,
        )
    }
}