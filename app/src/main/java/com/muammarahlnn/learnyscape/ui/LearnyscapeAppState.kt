package com.muammarahlnn.learnyscape.ui

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
import com.muammarahlnn.learnyscape.navigation.destination.Destination
import com.muammarahlnn.learnyscape.navigation.destination.Destination.ClassDestination
import com.muammarahlnn.learnyscape.navigation.destination.Destination.TopLevelDestination
import com.muammarahlnn.learnyscape.feature.aclass.navigation.CLASS_ROUTE
import com.muammarahlnn.learnyscape.feature.aclass.navigation.navigateToClassGraph
import com.muammarahlnn.learnyscape.feature.assignment.navigation.ASSIGNMENT_ROUTE
import com.muammarahlnn.learnyscape.feature.assignment.navigation.navigateToAssignment
import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_ROUTE
import com.muammarahlnn.learnyscape.feature.home.navigation.navigateToHomeGraph
import com.muammarahlnn.learnyscape.feature.member.navigation.MEMBER_ROUTE
import com.muammarahlnn.learnyscape.feature.member.navigation.navigateToMember
import com.muammarahlnn.learnyscape.feature.module.navigation.MODULE_ROUTE
import com.muammarahlnn.learnyscape.feature.module.navigation.navigateToModule
import com.muammarahlnn.learnyscape.feature.notifications.navigation.NOTIFICATIONS_ROUTE
import com.muammarahlnn.learnyscape.feature.profile.navigation.PROFILE_ROUTE
import com.muammarahlnn.learnyscape.feature.profile.navigation.navigateToProfile
import com.muammarahlnn.learnyscape.feature.quiz.navigation.QUIZ_ROUTE
import com.muammarahlnn.learnyscape.feature.quiz.navigation.navigateToQuiz
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.QUIZ_SESSION_ROUTE_WITH_ARGS
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.RESOURCE_DETAILS_ROUTE_WITH_ARGS
import com.muammarahlnn.learnyscape.feature.schedule.navigation.SCHEDULE_ROUTE
import com.muammarahlnn.learnyscape.feature.schedule.navigation.navigateToSchedule
import com.muammarahlnn.learnyscape.feature.search.navigation.SEARCH_ROUTE
import com.muammarahlnn.learnyscape.feature.search.navigation.navigateToSearch


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
        NOTIFICATIONS_ROUTE,
        RESOURCE_DETAILS_ROUTE_WITH_ARGS,
        QUIZ_SESSION_ROUTE_WITH_ARGS,
    )

    private val learnyscapeBottomBarRoutes = listOf(
        HOME_ROUTE, SEARCH_ROUTE, SCHEDULE_ROUTE, PROFILE_ROUTE,
    )

    private val classBottomBarRoutes = listOf(
        CLASS_ROUTE, MODULE_ROUTE, ASSIGNMENT_ROUTE, QUIZ_ROUTE, MEMBER_ROUTE,
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