package com.muammarahlnn.learnyscape.feature.classnavigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBarItem
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassDestination
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassNavHost


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigator, 15/09/2023 19.12 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassNavigatorRoute(
    navigateBack: () -> Unit,
    navigateToJoinRequests: (String) -> Unit,
    navigateToResourceDetails: (Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassNavigationViewModel = hiltViewModel(),
) {
    ClassNavigator(
        classId = viewModel.classId,
        navigateBack = navigateBack,
        navigateToJoinRequests = navigateToJoinRequests,
        navigateToResourceDetails = navigateToResourceDetails,
        navigateToResourceCreate = navigateToResourceCreate,
        modifier = modifier,
    )
}

@Composable
private fun ClassNavigator(
    classId: String,
    navigateBack: () -> Unit,
    navigateToJoinRequests: (String) -> Unit,
    navigateToResourceDetails: (Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    state: ClassNavigatorState = rememberClassNavigatorState(classId)
) {
    Scaffold(
        bottomBar = {
            ClassBottomBar(
                destinations = state.classDestinations,
                onNavigateToDestination = state::navigateToClassDestination,
                currentDestination = state.currentDestination
            )
        },
        modifier = modifier,
    ) { padding ->
        ClassNavHost(
            state = state,
            navigateBack = navigateBack,
            navigateToJoinRequests = navigateToJoinRequests,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun ClassBottomBar(
    destinations: List<ClassDestination>,
    onNavigateToDestination: (ClassDestination) -> Unit,
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
                        painter = painterResource(id = destination.iconId),
                        contentDescription = null
                    )
                },
            )
        }
    }
}

@Composable
private fun NavDestination?.isDestinationInHierarchy(destination: ClassDestination) =
    this?.hierarchy?.any {
        it.route?.equals(destination.route, true) ?: false
    } ?: false