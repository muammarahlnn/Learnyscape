package com.muammarahlnn.learnyscape.feature.resourcedetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsRoute
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsNavigation, 18/08/2023 01.19 by Muammar Ahlan Abimanyu
 */

private const val RESOURCE_DETAILS_ROUTE = "resource_details_route"
private const val RESOURCE_TYPE_ARG = "resource_type"
const val RESOURCE_DETAILS_ROUTE_WITH_ARGS =
    "$RESOURCE_DETAILS_ROUTE/{$RESOURCE_TYPE_ARG}"

private val urlCharacterEncoding = UTF_8.name()

internal class ResourceDetailsArgs(
    val resourceType: String,
) {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        URLDecoder.decode(checkNotNull(savedStateHandle[RESOURCE_TYPE_ARG]), urlCharacterEncoding)
    )
}

fun NavController.navigateToResourceDetails(
    resourceType: String,
) {
    val encodedResourceType = URLEncoder.encode(resourceType, urlCharacterEncoding)
    this.navigate("$RESOURCE_DETAILS_ROUTE/$encodedResourceType") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.resourceDetailsScreen(
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
            onBackClick = onBackClick,
        )
    }
}