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
const val assignmentRoute = "assignment_route"

fun NavController.navigateToAssignment(navOptions: NavOptions? = null) {
    this.navigate(assignmentRoute, navOptions)
}

fun NavGraphBuilder.assignmentScreen(
    onBackClick: () -> Unit,
) {
    composable(route = assignmentRoute) {
        AssignmentRoute(
            onBackClick = onBackClick
        )
    }
}