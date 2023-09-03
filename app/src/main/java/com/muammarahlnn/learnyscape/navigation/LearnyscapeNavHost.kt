package com.muammarahlnn.learnyscape.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscape.ui.LearnyscapeAppState
import com.muammarahlnn.learnyscape.feature.aclass.navigation.CLASS_GRAPH_ROUTE_PATTERN
import com.muammarahlnn.learnyscape.feature.aclass.navigation.classGraph
import com.muammarahlnn.learnyscape.feature.aclass.navigation.navigateToClassGraph
import com.muammarahlnn.learnyscape.feature.assignment.navigation.assignmentScreen
import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_GRAPH_ROUTE_PATTERH
import com.muammarahlnn.learnyscape.feature.home.navigation.homeGraph
import com.muammarahlnn.learnyscape.feature.member.navigation.memberScreen
import com.muammarahlnn.learnyscape.feature.module.navigation.moduleScreen
import com.muammarahlnn.learnyscape.feature.notifications.navigation.navigateToNotifications
import com.muammarahlnn.learnyscape.feature.notifications.navigation.notificationsScreen
import com.muammarahlnn.learnyscape.feature.profile.navigation.profileScreen
import com.muammarahlnn.learnyscape.feature.quiz.navigation.quizScreen
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.navigateToQuizSession
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.quizSessionScreen
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.navigateToResourceDetails
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.resourceDetailsScreen
import com.muammarahlnn.learnyscape.feature.schedule.navigation.scheduleScreen
import com.muammarahlnn.learnyscape.feature.search.navigation.searchScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeNavHost, 22/07/2023 08.54 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeNavHost(
    appState: LearnyscapeAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_GRAPH_ROUTE_PATTERH,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            onNotificationsClick = {
                navController.navigateToNotifications()
            },
            onClassClick = {
                navController.navigateToClassGraph()
            },
        ) {
            notificationsScreen(
                onNotificationClick = toResourceDetails(navController),
                onBackClick = navController::popBackStack
            )
        }
        searchScreen()
        scheduleScreen()
        profileScreen()

        classGraph(
            onPostClick = toResourceDetails(navController),
            onBackClick = classGraphOnBackClick(navController)
        ) {
            moduleScreen(
                onModuleClick = toResourceDetails(navController),
                onBackClick = classGraphOnBackClick(navController)
            )
            assignmentScreen(
                onAssignmentClick = toResourceDetails(navController),
                onBackClick = classGraphOnBackClick(navController)
            )
            quizScreen(
                onQuizClick = toResourceDetails(navController),
                onBackClick = classGraphOnBackClick(navController)
            )
            memberScreen(
                onBackClick = classGraphOnBackClick(navController)
            )
            resourceDetailsScreen(
                onConfirmStartQuizDialog = { quizName, quizDuration ->
                    navController.navigateToQuizSession(quizName, quizDuration)
                },
                onBackClick = navController::popBackStack
            )
            quizSessionScreen(
                onConfirmSubmitAnswerDialog = navController::popBackStack
            )
        }
    }
}

private fun toResourceDetails(navController: NavHostController): (String) -> Unit = { resourceType ->
    navController.navigateToResourceDetails(resourceType)
}

private fun classGraphOnBackClick(navController: NavHostController): () -> Unit = {
    navController.popBackStack(
        route = CLASS_GRAPH_ROUTE_PATTERN,
        inclusive = true,
    )
}

