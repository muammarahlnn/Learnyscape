package com.muammarahlnn.learnyscape.feature.joinrequest.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestRoute

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestNavigation, 16/12/2023 04.34
 */

private const val JOIN_REQUEST_ROUTE = "join_request_route"
private const val CLASS_ID_ARG = "class_id"
private const val JOIN_REQUEST_ROUTE_WITH_ARGS =
    "$JOIN_REQUEST_ROUTE/{$CLASS_ID_ARG}"

internal class JoinRequestArgs(
    val classId: String,
) {

    constructor(savedStateHandle: SavedStateHandle) : this(
        classId = checkNotNull(savedStateHandle[CLASS_ID_ARG])
    )
}

fun NavController.navigateToJoinRequest(
    classId: String,
) {
    this.navigate("$JOIN_REQUEST_ROUTE/$classId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.joinRequestScreen(
    navigateBack: () -> Unit,
) {
    composable(
        route = JOIN_REQUEST_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(CLASS_ID_ARG) {
                type = NavType.StringType
            },
        ),
    ) {
        JoinRequestRoute(navigateBack = navigateBack)
    }
}