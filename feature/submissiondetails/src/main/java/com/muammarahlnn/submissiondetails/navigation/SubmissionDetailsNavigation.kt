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
private const val STUDENT_ID_ARG = "student_id"
private const val STUDENT_NAME_ARG = "student_name"
private const val SUBMISSION_DETAILS_ROUTE_WITH_ARGS =
    "$SUBMISSION_DETAILS_ROUTE?$SUBMISSION_ID_ARG={$SUBMISSION_ID_ARG}/{$SUBMISSION_TYPE_ORDINAL_ARG}/{$STUDENT_ID_ARG}/{$STUDENT_NAME_ARG}"

internal class SubmissionDetailsArgs(
    val submissionTypeOrdinal: Int,
    val submissionId: String?,
    val studentId: String,
    val studentName: String,
) {

    constructor(savedStateHandle: SavedStateHandle) : this(
        submissionTypeOrdinal = checkNotNull(savedStateHandle[SUBMISSION_TYPE_ORDINAL_ARG]),
        submissionId = savedStateHandle[SUBMISSION_ID_ARG],
        studentId = checkNotNull(savedStateHandle[STUDENT_ID_ARG]),
        studentName = checkNotNull(savedStateHandle[STUDENT_NAME_ARG]),
    )
}

fun NavController.navigateToSubmissionDetails(
    submissionTypeOrdinal: Int,
    submissionId: String,
    studentId: String,
    studentName: String,
) {
    this.navigate("$SUBMISSION_DETAILS_ROUTE?$SUBMISSION_ID_ARG=$submissionId/$submissionTypeOrdinal/$studentId/$studentName") {
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
                nullable = true
            },
            navArgument(STUDENT_ID_ARG) {
                type = NavType.StringType
            },
            navArgument(STUDENT_NAME_ARG) {
                type = NavType.StringType
            },
        )
    ) {
        SubmissionDetailsRoute(
            navigateBack = navigateBack,
        )
    }
}