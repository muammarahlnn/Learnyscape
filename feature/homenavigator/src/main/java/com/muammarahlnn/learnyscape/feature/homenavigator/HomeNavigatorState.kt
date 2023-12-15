package com.muammarahlnn.learnyscape.feature.homenavigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.core.model.data.UserRole
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.executeForStudent
import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_ROUTE
import com.muammarahlnn.learnyscape.feature.home.navigation.navigateToHome
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HomeDestination
import com.muammarahlnn.learnyscape.feature.profile.navigation.PROFILE_ROUTE
import com.muammarahlnn.learnyscape.feature.profile.navigation.navigateToProfile
import com.muammarahlnn.learnyscape.feature.schedule.navigation.SCHEDULE_ROUTE
import com.muammarahlnn.learnyscape.feature.schedule.navigation.navigateToSchedule
import com.muammarahlnn.learnyscape.feature.search.navigation.SEARCH_ROUTE
import com.muammarahlnn.learnyscape.feature.search.navigation.navigateToSearch


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigatorState, 15/09/2023 18.41 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun rememberHomeNavigatorState(
    navController: NavHostController = rememberNavController(),
    user: UserModel = LocalUserModel.current,
): HomeNavigatorState {
    return remember(
        navController,
        user,
    ) {
        HomeNavigatorState(
            navController,
            user,
        )
    }
}

@Stable
internal class HomeNavigatorState(
    val navController: NavHostController,
    private val user: UserModel,
) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val homeDestinations = mutableListOf(
        HomeDestination.HOME,
        HomeDestination.SCHEDULE,
        HomeDestination.PROFILE,
    ).also {
        executeForStudent(user) {
            it.add(
                index = HomeDestination.SEARCH.ordinal,
                element = HomeDestination.SEARCH
            )
        }
    }.toList()

    val currentHomeDestination: HomeDestination?
        @Composable
        get() = when (currentDestination?.route) {
            HOME_ROUTE -> HomeDestination.HOME
            SEARCH_ROUTE -> HomeDestination.SEARCH
            SCHEDULE_ROUTE -> HomeDestination.SCHEDULE
            PROFILE_ROUTE -> HomeDestination.PROFILE
            else -> null
        }

    fun navigateToHomeDestination(homeDestination: HomeDestination) {
        val navOptions = navOptions {
            popUpTo(homeDestination.route)
            launchSingleTop = true
            restoreState = true
        }

        when (homeDestination) {
            HomeDestination.HOME -> navController.navigateToHome(navOptions)
            HomeDestination.SEARCH -> navController.navigateToSearch(navOptions)
            HomeDestination.SCHEDULE -> navController.navigateToSchedule(navOptions)
            HomeDestination.PROFILE -> navController.navigateToProfile(navOptions)
        }
    }
}