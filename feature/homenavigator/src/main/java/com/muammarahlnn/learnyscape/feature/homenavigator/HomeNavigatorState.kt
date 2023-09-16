package com.muammarahlnn.learnyscape.feature.homenavigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.muammarahlnn.learnyscape.feature.home.navigation.navigateToHome
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HomeDestination
import com.muammarahlnn.learnyscape.feature.profile.navigation.navigateToProfile
import com.muammarahlnn.learnyscape.feature.schedule.navigation.navigateToSchedule
import com.muammarahlnn.learnyscape.feature.search.navigation.navigateToSearch


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigatorState, 15/09/2023 18.41 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun rememberHomeNavigatorState(
    navController: NavHostController = rememberNavController()
): HomeNavigatorState = remember(navController) {
    HomeNavigatorState(navController)
}

@Stable
internal class HomeNavigatorState(
    val navController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val homeDestinations: List<HomeDestination> = HomeDestination.values().asList()

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