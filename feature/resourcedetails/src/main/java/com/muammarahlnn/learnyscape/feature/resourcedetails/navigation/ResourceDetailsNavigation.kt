package com.muammarahlnn.learnyscape.feature.resourcedetails.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsController
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsNavigation, 18/08/2023 01.19 by Muammar Ahlan Abimanyu
 */

private const val RESOURCE_DETAILS_ROUTE = "resource_details_route"
private const val RESOURCE_ID_ARG = "resource_id"
private const val RESOURCE_TYPE_ORDINAL_ARG = "resource_type_ordinal"
private const val RESOURCE_DETAILS_ROUTE_WITH_ARGS =
    "$RESOURCE_DETAILS_ROUTE/{$RESOURCE_ID_ARG}/{$RESOURCE_TYPE_ORDINAL_ARG}"

internal class ResourceDetailsArgs(
    val resourceId: String,
    val resourceTypeOrdinal: Int,
) {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        resourceId = checkNotNull(savedStateHandle[RESOURCE_ID_ARG]),
        resourceTypeOrdinal = checkNotNull(savedStateHandle[RESOURCE_TYPE_ORDINAL_ARG]),
    )
}

fun NavController.navigateToResourceDetails(
    resourceId: String,
    resourceTypeOrdinal: Int,
) {
    this.navigate("$RESOURCE_DETAILS_ROUTE/$resourceId/$resourceTypeOrdinal") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.resourceDetailScreen(
    navigateBack: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToQuizSession: (String, Int, String, Int) -> Unit,
    navigateToSubmissionDetails: (Int, String, String, String) -> Unit,
) {
    composable(
        route = RESOURCE_DETAILS_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(RESOURCE_ID_ARG) {
                type = NavType.StringType
            },
            navArgument(RESOURCE_TYPE_ORDINAL_ARG) {
                type = NavType.IntType
            },
        ),
    ) {
        ResourceDetailsRoute(
            controller = ResourceDetailsController(
                scope = rememberCoroutineScope(),
                navigateBack = navigateBack,
                navigateToCamera = navigateToCamera,
                navigateToQuizSession = navigateToQuizSession,
                navigateToSubmissionDetails = navigateToSubmissionDetails,
            ),
        )
    }
}