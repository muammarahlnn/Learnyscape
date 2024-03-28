package com.muammarahlnn.learnyscape.feature.assignment.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.assignment.AssignmentController
import com.muammarahlnn.learnyscape.feature.assignment.AssignmentRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AssignmentNavigation, 03/08/2023 15.54 by Muammar Ahlan Abimanyu
 */
const val ASSIGNMENT_ROUTE = "assignment_route"

fun NavController.navigateToAssignment(navOptions: NavOptions? = null) {
    this.navigate(ASSIGNMENT_ROUTE, navOptions)
}

fun NavGraphBuilder.assignmentScreen(
    classId: String,
    navigateBack: () -> Unit,
    navigateToResourceDetails: (String, String, Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
) {
    composable(route = ASSIGNMENT_ROUTE) {
        AssignmentRoute(
            classId = classId,
            controller = AssignmentController(
                scope = rememberCoroutineScope(),
                navigateBack = navigateBack,
                navigateToResourceDetails = navigateToResourceDetails,
                navigateToResourceCreate = navigateToResourceCreate,
            )
        )
    }
}