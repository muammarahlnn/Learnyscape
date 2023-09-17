package com.muammarahlnn.learnyscape.feature.assignment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
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
    onAssignmentClick: (Int) -> Unit,
) {
    composable(route = ASSIGNMENT_ROUTE) {
        AssignmentRoute(
            onAssignmentClick = onAssignmentClick,
        )
    }
}