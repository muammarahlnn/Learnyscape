package com.muammarahlnn.learnyscape.feature.homenavigator.navigation

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

@Composable
internal fun HomeNavHost(
    state: HomeNavigatorState,
    navigateToNotifications: () -> Unit,
    navigateToClass: (String) -> Unit,
    navigateToPendingRequestClass: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToChangePassword: () -> Unit,
    navigateToLogin: () -> Unit,
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
            navigateToNotifications = navigateToNotifications,
            navigateToClass = navigateToClass,
        )
        searchScreen(
            navigateToPendingRequestClass = navigateToPendingRequestClass
        )
        scheduleScreen(
            navigateToClass = navigateToClass,
        )
        profileScreen(
            navigateToCamera = navigateToCamera,
            navigateToChangePassword = navigateToChangePassword,
            navigateToLogin = navigateToLogin,
        )
    }
}