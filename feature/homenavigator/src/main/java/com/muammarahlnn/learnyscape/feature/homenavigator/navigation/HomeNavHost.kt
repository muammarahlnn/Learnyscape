package com.muammarahlnn.learnyscape.feature.homenavigator.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_ROUTE
import com.muammarahlnn.learnyscape.feature.home.navigation.homeScreen
import com.muammarahlnn.learnyscape.feature.homenavigator.HomeNavigatorState
import com.muammarahlnn.learnyscape.feature.profile.navigation.profileScreen
import com.muammarahlnn.learnyscape.feature.schedule.navigation.scheduleScreen
import com.muammarahlnn.learnyscape.feature.search.navigation.searchScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavHost, 15/09/2023 19.25 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeNavHost(
    state: HomeNavigatorState,
    scrollBehavior: TopAppBarScrollBehavior,
    onClassClick: () -> Unit,
    onCameraActionClick: () -> Unit,
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
            scrollBehavior = scrollBehavior,
            onClassClick = onClassClick,
        )
        searchScreen(
            scrollBehavior = scrollBehavior,
        )
        scheduleScreen(
            scrollBehavior = scrollBehavior,
            onClassClick = onClassClick,
        )
        profileScreen(
            scrollBehavior = scrollBehavior,
            onCameraActionClick = onCameraActionClick,
        )
    }
}