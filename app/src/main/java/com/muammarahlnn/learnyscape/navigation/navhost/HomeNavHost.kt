package com.muammarahlnn.learnyscape.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_ROUTE
import com.muammarahlnn.learnyscape.feature.home.navigation.homeScreen
import com.muammarahlnn.learnyscape.feature.profile.navigation.profileScreen
import com.muammarahlnn.learnyscape.feature.schedule.navigation.scheduleScreen
import com.muammarahlnn.learnyscape.feature.search.navigation.searchScreen
import com.muammarahlnn.learnyscape.ui.home.HomeNavigatorState


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavHost, 14/09/2023 21.00 by Muammar Ahlan Abimanyu
 */

@Composable
fun HomeNavHost(
    state: HomeNavigatorState,
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    val navController = state.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onNotificationsClick = onNotificationsClick,
            onClassClick = onClassClick,
        )
        searchScreen()
        scheduleScreen()
        profileScreen()
    }
}