package com.muammarahlnn.learnyscacpe.ui

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
import com.muammarahlnn.learnyscacpe.navigation.LearnyscapeNavHost
import com.muammarahlnn.learnyscacpe.navigation.destination.ClassDestination
import com.muammarahlnn.learnyscacpe.navigation.destination.Destination
import com.muammarahlnn.learnyscacpe.navigation.destination.TopLevelDestination
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeBackground
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBarItem


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeApp, 19/07/2023 22.11 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeApp(
    appState: LearnyscapeAppState = rememberLearnyscapeAppState(),
) {
    LearnyscapeBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                ShowBottomBar(appState = appState)
            }
        ) { innerPadding ->
            LearnyscapeNavHost(
                appState = appState,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun ShowBottomBar(appState: LearnyscapeAppState) {
    if (!appState.shouldNotShowBottomBar) {
        LearnyscapeBottomBar(
            destinations = appState.currentDestinations,
            onNavigateToDestination = appState::navigateToDestination,
            currentDestination = appState.currentDestination
        )
//        when {
//            appState.showLearnyscapeBottomBar -> {
//            }

//            appState.showClassBottomBar -> {
//                ClassBottomBar(
//                    destinations = appState.classDestinations,
//                    onNavigateToDestination = appState::navigateToClassDestination,
//                    currentDestination = appState.currentDestination
//                )
//            }
        }
    }
//}

@Composable
private fun LearnyscapeBottomBar(
    destinations: List<Destination>,
    onNavigateToDestination: (Destination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    LearnyscapeNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = when (destination) {
                is TopLevelDestination ->
                    currentDestination.isTopLevelDestinationInHierarchy(destination)
                is ClassDestination ->
                    currentDestination.isClassDestinationInHierarchy(destination)
                else -> throw IllegalArgumentException("")
            }
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
                },
            )
        }
    }
}

@Composable
private fun ClassBottomBar(
    destinations: List<ClassDestination>,
    onNavigateToDestination: (ClassDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    LearnyscapeNavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isClassDestinationInHierarchy(destination)
            LearnyscapeNavigationBarItem(
                selected = selected,
                onClick = { 
                    onNavigateToDestination(destination)
                },
                icon = { 
                    Icon(
                        painter = painterResource(id = destination.unselectedIconId),
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = destination.selectedIconId),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

private fun NavDestination?.isClassDestinationInHierarchy(destination: ClassDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false