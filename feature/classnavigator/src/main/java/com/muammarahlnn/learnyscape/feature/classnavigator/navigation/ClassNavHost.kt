package com.muammarahlnn.learnyscape.feature.classnavigator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscape.feature.aclass.navigation.CLASS_ROUTE
import com.muammarahlnn.learnyscape.feature.aclass.navigation.classScreen
import com.muammarahlnn.learnyscape.feature.assignment.navigation.assignmentScreen
import com.muammarahlnn.learnyscape.feature.classnavigator.ClassNavigatorState
import com.muammarahlnn.learnyscape.feature.member.navigation.memberScreen
import com.muammarahlnn.learnyscape.feature.module.navigation.moduleScreen
import com.muammarahlnn.learnyscape.feature.quiz.navigation.quizScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavHost, 15/09/2023 19.19 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassNavHost(
    state: ClassNavigatorState,
    navigateBack: () -> Unit,
    navigateToJoinRequests: (String) -> Unit,
    navigateToResourceDetails: (String, String, Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = CLASS_ROUTE,
) {
    val navController = state.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        classScreen(
            classId = state.classId,
            navigateBack = navigateBack,
            navigateToJoinRequests = navigateToJoinRequests,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
        )
        moduleScreen(
            classId = state.classId,
            navigateBack = navigateBack,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
        )
        assignmentScreen(
            classId = state.classId,
            navigateBack = navigateBack,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
        )
        quizScreen(
            classId = state.classId,
            navigateBack = navigateBack,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
        )
        memberScreen(
            classId = state.classId,
            navigateBack = navigateBack,
        )
    }
}