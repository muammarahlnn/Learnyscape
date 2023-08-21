package com.muammarahlnn.learnyscacpe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscacpe.ui.LearnyscapeAppState
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
                onBackClick = navController::popBackStack
            )
        }
        searchScreen()
        scheduleScreen()
        profileScreen()

        classGraph(
            onPostClick = { resourceType ->
                navController.navigateToResourceDetails(resourceType)
            },
            onBackClick = navController::popBackStack
        ) {
            moduleScreen(
                onModuleClick = { resourceType ->
                    navController.navigateToResourceDetails(resourceType)
                },
                onBackClick = navController::popBackStack
            )
            assignmentScreen(
                onAssignmentClick = { resourceType ->
                    navController.navigateToResourceDetails(resourceType)
                },
                onBackClick = navController::popBackStack
            )
            quizScreen(
                onQuizClick = { resourceType ->
                    navController.navigateToResourceDetails(resourceType)
                },
                onBackClick = navController::popBackStack
            )
            memberScreen(
                onBackClick = navController::popBackStack
            )
            resourceDetailsScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}