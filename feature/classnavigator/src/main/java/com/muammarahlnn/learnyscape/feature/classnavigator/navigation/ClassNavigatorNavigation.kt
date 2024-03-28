package com.muammarahlnn.learnyscape.feature.classnavigator.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.classnavigator.ClassNavigatorRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigatorNavigation, 15/09/2023 19.45 by Muammar Ahlan Abimanyu
 */
private const val CLASS_NAVIGATOR_ROUTE = "class_navigator_route"
private const val CLASS_ID_ARG = "class_id"
const val CLASS_NAVIGATOR_ROUTE_WITH_ARGS =
    "$CLASS_NAVIGATOR_ROUTE/{$CLASS_ID_ARG}"

class ClassNavigatorArgs(
    val classId: String,
) {

    constructor(savedStateHandle: SavedStateHandle) : this(
        classId = checkNotNull(savedStateHandle[CLASS_ID_ARG])
    )
}

fun NavController.navigateToClassNavigator(
    classId: String,
) {
    this.navigate("$CLASS_NAVIGATOR_ROUTE/$classId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.classNavigator(
    navigateBack: () -> Unit,
    navigateToJoinRequests: (String) -> Unit,
    navigateToResourceDetails: (String, String, Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
) {
    composable(
        route = CLASS_NAVIGATOR_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(CLASS_ID_ARG) {
                type = NavType.StringType
            }
        )
    ) {
        ClassNavigatorRoute(
            navigateBack = navigateBack,
            navigateToJoinRequests = navigateToJoinRequests,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
        )
    }
}