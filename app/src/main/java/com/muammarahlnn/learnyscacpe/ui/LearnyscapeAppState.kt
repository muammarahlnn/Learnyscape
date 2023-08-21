package com.muammarahlnn.learnyscacpe.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.muammarahlnn.learnyscacpe.navigation.destination.Destination
import com.muammarahlnn.learnyscacpe.navigation.destination.Destination.ClassDestination
import com.muammarahlnn.learnyscacpe.navigation.destination.Destination.TopLevelDestination
import com.muammarahlnn.learnyscape.feature.aclass.navigation.classRoute
import com.muammarahlnn.learnyscape.feature.aclass.navigation.navigateToClassGraph
import com.muammarahlnn.learnyscape.feature.assignment.navigation.assignmentRoute
import com.muammarahlnn.learnyscape.feature.assignment.navigation.navigateToAssignment
import com.muammarahlnn.learnyscape.feature.home.navigation.homeRoute
import com.muammarahlnn.learnyscape.feature.home.navigation.navigateToHomeGraph
import com.muammarahlnn.learnyscape.feature.member.navigation.memberRoute
import com.muammarahlnn.learnyscape.feature.member.navigation.navigateToMember
import com.muammarahlnn.learnyscape.feature.module.navigation.moduleRoute
import com.muammarahlnn.learnyscape.feature.module.navigation.navigateToModule
import com.muammarahlnn.learnyscape.feature.notifications.navigation.notificationsRoute
import com.muammarahlnn.learnyscape.feature.profile.navigation.navigateToProfile
import com.muammarahlnn.learnyscape.feature.profile.navigation.profileRoute
import com.muammarahlnn.learnyscape.feature.quiz.navigation.navigateToQuiz
import com.muammarahlnn.learnyscape.feature.quiz.navigation.quizRoute
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.resourceDetailsRoute
import com.muammarahlnn.learnyscape.feature.schedule.navigation.navigateToSchedule
import com.muammarahlnn.learnyscape.feature.schedule.navigation.scheduleRoute
import com.muammarahlnn.learnyscape.feature.search.navigation.navigateToSearch
import com.muammarahlnn.learnyscape.feature.search.navigation.searchRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeAppState, 20/07/2023 17.50 by Muammar Ahlan Abimanyu
 */

@Composable
fun rememberLearnyscapeAppState(
    navController: NavHostController = rememberNavController(),
): LearnyscapeAppState {
    return remember(navController) {
        LearnyscapeAppState(navController)
    }
}

@Stable
class LearnyscapeAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentBottomBarDestinations: List<Destination>
        @Composable
        get() = when {
            showLearnyscapeBottomBar -> topLevelDestinations
            showClassBottomBar -> classDestinations
            else -> listOf() // TODO: fix this if use IllegalArgumentException()
        }

    val currentStatusBarColor: Color
        @Composable
        get() = when {
            showLearnyscapeBottomBar -> MaterialTheme.colorScheme.primary
            showClassBottomBar -> MaterialTheme.colorScheme.onPrimary
            else -> MaterialTheme.colorScheme.primary
        }

    private val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    private val classDestinations: List<ClassDestination> = ClassDestination.values().asList()

    private val currentRoute: String?
        @Composable
        get() = currentDestination?.route

    private val withoutBottomBarRoutes = listOf(
        notificationsRoute, resourceDetailsRoute,
    )

    private val learnyscapeBottomBarRoutes = listOf(
        homeRoute, searchRoute, scheduleRoute, profileRoute,
    )

    private val classBottomBarRoutes = listOf(
        classRoute, moduleRoute, assignmentRoute, quizRoute, memberRoute,
    )

    private val showLearnyscapeBottomBar: Boolean
        @Composable
        get() = currentRoute in learnyscapeBottomBarRoutes

    private val showClassBottomBar: Boolean
        @Composable
        get() = currentRoute in classBottomBarRoutes

    val shouldNotShowBottomBar: Boolean
        @Composable
        get() = currentRoute in withoutBottomBarRoutes

    fun navigateToDestination(destination: Destination) {
        val destinationNavOptions = navOptions {
            popUpTo(destination.route)
            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            TopLevelDestination.HOME -> navController.navigateToHomeGraph(destinationNavOptions)
            TopLevelDestination.SEARCH -> navController.navigateToSearch(destinationNavOptions)
            TopLevelDestination.SCHEDULE -> navController.navigateToSchedule(destinationNavOptions)
            TopLevelDestination.PROFILE -> navController.navigateToProfile(destinationNavOptions)

            ClassDestination.CLASS -> navController.navigateToClassGraph(destinationNavOptions)
            ClassDestination.MODULE -> navController.navigateToModule(destinationNavOptions)
            ClassDestination.ASSIGNMENT -> navController.navigateToAssignment(destinationNavOptions)
            ClassDestination.QUIZ -> navController.navigateToQuiz(destinationNavOptions)
            ClassDestination.MEMBER -> navController.navigateToMember(destinationNavOptions)
        }
    }
}