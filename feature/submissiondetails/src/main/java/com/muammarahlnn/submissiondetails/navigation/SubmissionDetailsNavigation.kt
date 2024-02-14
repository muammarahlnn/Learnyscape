package com.muammarahlnn.submissiondetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.submissiondetails.SubmissionDetailsRoute

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsNavigation, 02/02/2024 21.13
 */
private const val SUBMISSION_DETAILS_ROUTE = "submission_details_route"
private const val SUBMISSION_TYPE_ORDINAL_ARG = "submission_type_ordinal"
private const val SUBMISSION_ID_ARG = "submission_id"
private const val SUBMISSION_DETAILS_ROUTE_WITH_ARGS =
    "$SUBMISSION_DETAILS_ROUTE/{$SUBMISSION_TYPE_ORDINAL_ARG}/{$SUBMISSION_ID_ARG}"

internal class SubmissionDetailsArgs(
    val submissionTypeOrdinal: Int,
    val submissionId: String,
) {

    constructor(savedStateHandle: SavedStateHandle) : this(
        submissionTypeOrdinal = checkNotNull(savedStateHandle[SUBMISSION_TYPE_ORDINAL_ARG]),
        submissionId = checkNotNull(savedStateHandle[SUBMISSION_ID_ARG]),
    )
}

fun NavController.navigateToSubmissionDetails(
    submissionTypeOrdinal: Int,
    submissionId: String,
) {
    this.navigate("$SUBMISSION_DETAILS_ROUTE/$submissionTypeOrdinal/$submissionId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.submissionDetailsScreen(
    navigateBack: () -> Unit,
) {
    composable(
        route = SUBMISSION_DETAILS_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(SUBMISSION_TYPE_ORDINAL_ARG) {
                type = NavType.IntType
            },
            navArgument(SUBMISSION_ID_ARG) {
                type = NavType.StringType
            },
        )
    ) {
        SubmissionDetailsRoute(
            navigateBack = navigateBack,
        )
    }
}