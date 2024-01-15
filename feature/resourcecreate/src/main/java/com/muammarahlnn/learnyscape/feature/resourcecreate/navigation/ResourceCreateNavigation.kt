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
private const val CLASS_ID_ARG = "class_id"
private const val RESOURCE_TYPE_ORDINAL_ARG = "resource_type_ordinal"
private const val RESOURCE_CREATE_ROUTE_WITH_ARGS =
    "$RESOURCE_CREATE_ROUTE/{$CLASS_ID_ARG}/{$RESOURCE_TYPE_ORDINAL_ARG}"

internal class ResourceCreateArgs(
    val classId: String,
    val resourceTypeOrdinal: Int,
) {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        classId = checkNotNull(savedStateHandle[CLASS_ID_ARG]),
        resourceTypeOrdinal = checkNotNull(savedStateHandle[RESOURCE_TYPE_ORDINAL_ARG])
    )
}

fun NavController.navigateToResourceCreate(
    classId: String,
    resourceTypeOrdinal: Int,
) {
    this.navigate("$RESOURCE_CREATE_ROUTE/$classId/$resourceTypeOrdinal") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.resourceCreateScreen(
    navigateBack: () -> Unit,
    navigateToCamera: () -> Unit,
) {
    composable(
        route = RESOURCE_CREATE_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(CLASS_ID_ARG) {
                type = NavType.StringType
            },
            navArgument(RESOURCE_TYPE_ORDINAL_ARG) {
                type = NavType.IntType
            }
        )
    ) {
        ResourceCreateRoute(
            navigateBack = navigateBack,
            navigateToCamera = navigateToCamera,
        )
    }
}