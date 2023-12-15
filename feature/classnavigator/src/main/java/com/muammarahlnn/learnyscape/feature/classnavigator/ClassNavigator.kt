package com.muammarahlnn.learnyscape.feature.classnavigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBarItem
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassDestination
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassNavHost
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigator, 15/09/2023 19.12 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassNavigatorRoute(
    onResourceClassClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClassNavigator(
        onResourceClassClick = onResourceClassClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClassNavigator(
    onResourceClassClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ClassNavigatorState = rememberClassNavigatorState()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            val classDestination = state.currentClassDestination
            if (classDestination != null && classDestination != ClassDestination.CLASS) {
                ClassTopAppBar(
                    title = stringResource(id = classDestination.titleId),
                    onBackClick = onBackClick,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
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
            onResourceClassClick = onResourceClassClick,
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = title,
        navigationIconRes = designSystemR.drawable.ic_arrow_back_bold,
        navigationIconContentDescription = stringResource(
            id = designSystemR.string.navigation_back_icon_description,
        ),
        colors = LearnyscapeTopAppbarDefaults.classTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        onNavigationClick = onBackClick,
        modifier = modifier,
    )
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