package com.muammarahlnn.learnyscacpe.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscacpe.ui.LearnyscapeAppState
import com.muammarahlnn.learnyscape.feature.home.navigation.homeGraph
import com.muammarahlnn.learnyscape.feature.home.navigation.homeGraphRoutePattern
import com.muammarahlnn.learnyscape.feature.notifications.navigation.navigateToNotifications
import com.muammarahlnn.learnyscape.feature.notifications.navigation.notificationsScreen
import com.muammarahlnn.learnyscape.feature.profile.navigation.profileScreen
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
    startDestination: String = homeGraphRoutePattern,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
    ) {
        homeGraph(
            onNotificationsClick = {
                navController.navigateToNotifications()
            }
        ) {
            notificationsScreen()
        }
        searchScreen()
        scheduleScreen()
        profileScreen()
    }
}