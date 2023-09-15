package com.muammarahlnn.learnyscape.ui.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBarItem
import com.muammarahlnn.learnyscape.navigation.navhost.HomeNavHost
import com.muammarahlnn.learnyscape.navigation.destination.HomeDestination


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigator, 12/09/2023 18.37 by Muammar Ahlan Abimanyu
 */

@Composable
fun HomeNavigatorRoute(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeNavigator(
        onNotificationsClick = onNotificationsClick,
        onClassClick = onClassClick,
        modifier = modifier,
    )
}

@Composable
fun HomeNavigator(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: HomeNavigatorState = rememberHomeNavigatorState()
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            HomeBottomBar(
                destinations = state.homeDestinations,
                onNavigateToDestination = state::navigateToHomeDestination,
                currentDestination = state.currentDestination
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        HomeNavHost(
            state = state,
            onNotificationsClick = onNotificationsClick,
            onClassClick = onClassClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun HomeBottomBar(
    destinations: List<HomeDestination>,
    onNavigateToDestination: (HomeDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    LearnyscapeNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isDestinationInHierarchy(destination)
            LearnyscapeNavigationBarItem(
                selected = selected,
                onClick = {
                    onNavigateToDestination(destination)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.unselectedIconId),
                        contentDescription = null
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = destination.selectedIconId),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
private fun NavDestination?.isDestinationInHierarchy(destination: HomeDestination) =
    this?.hierarchy?.any {
        it.route?.equals(destination.route, true) ?: false
    } ?: false