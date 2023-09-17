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
    onResourceClassClick: (Int) -> Unit,
    onBackClick: () -> Unit,
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
            onPostClick = onResourceClassClick,
            onBackClick = onBackClick,
        )
        moduleScreen(
            onModuleClick = onResourceClassClick,
            onBackClick = onBackClick,
        )
        assignmentScreen(
            onAssignmentClick = onResourceClassClick,
            onBackClick = onBackClick,
        )
        quizScreen(
            onQuizClick = onResourceClassClick,
            onBackClick = onBackClick,
        )
        memberScreen(
            onBackClick = onBackClick,
        )
    }
}