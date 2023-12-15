package com.muammarahlnn.learnyscape.feature.homenavigator

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeNavigationBarItem
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.model.data.UserRole
import com.muammarahlnn.learnyscape.core.ui.LearnyscapeLogoText
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.StudentOnlyComposable
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HomeDestination
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HomeNavHost
import com.muammarahlnn.learnyscape.feature.home.R as homeR
import com.muammarahlnn.learnyscape.feature.search.R as searchR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNavigator, 15/09/2023 18.37 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun HomeNavigatorRoute(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    onCameraActionClick: () -> Unit,
    onPendingClassRequestClick: () -> Unit,
    onChangePasswordButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeNavigator(
        onNotificationsClick = onNotificationsClick,
        onClassClick = onClassClick,
        onCameraActionClick = onCameraActionClick,
        onPendingClassRequestClick = onPendingClassRequestClick,
        onChangePasswordButtonClick = onChangePasswordButtonClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeNavigator(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    onCameraActionClick: () -> Unit,
    onPendingClassRequestClick: () -> Unit,
    onChangePasswordButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: HomeNavigatorState = rememberHomeNavigatorState()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            val destination = state.currentHomeDestination
            if (destination != null) {
                HomeNavigatorTopAppBar(
                    destination = destination,
                    scrollBehavior = scrollBehavior,
                    onNotificationsClick = onNotificationsClick,
                    onPendingClassRequestClick = onPendingClassRequestClick,
                )
            }
        },
        bottomBar = {
            HomeNavigatorBottomBar(
                destinations = state.homeDestinations,
                onNavigateToDestination = state::navigateToHomeDestination,
                currentDestination = state.currentDestination
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        HomeNavHost(
            state = state,
            scrollBehavior = scrollBehavior,
            onClassClick = onClassClick,
            onCameraActionClick = onCameraActionClick,
            onChangePasswordButtonClick = onChangePasswordButtonClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeNavigatorTopAppBar(
    destination: HomeDestination,
    scrollBehavior: TopAppBarScrollBehavior,
    onNotificationsClick: () -> Unit,
    onPendingClassRequestClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (destination) {
        HomeDestination.HOME ->  {
            HomeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNotificationsClick = onNotificationsClick,
                modifier = modifier,
            )
        }

        HomeDestination.SEARCH -> {
            SearchTopAppBar(
                scrollBehavior = scrollBehavior,
                onPendingClassRequestClick = onPendingClassRequestClick,
                modifier = modifier,
            )
        }

        else -> {
            DefaultHomeNavigatorTopAppBar(
                titleId = destination.titleId,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val user = LocalUserModel.current
    val homeTopAppBarModifier = modifier.shadow(
        elevation = 2.dp,
        shape = RoundedCornerShape(
            bottomStart = 16.dp,
            bottomEnd = 16.dp,
        )
    )
    when (user.role) {
        UserRole.STUDENT -> StudentHomeTopAppBar(
            onNotificationsClick = onNotificationsClick,
            modifier = homeTopAppBarModifier,
            scrollBehavior = scrollBehavior,
        )
        UserRole.LECTURER -> LecturerHomeTopAppBar(
            modifier = homeTopAppBarModifier,
            scrollBehavior = scrollBehavior,
        )
        UserRole.NOT_LOGGED_IN -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentHomeTopAppBar(
    onNotificationsClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            LearnyscapeLogoText()
        },
        actions = {
            IconButton(onClick = onNotificationsClick) {
                Icon(
                    painter = painterResource(id = homeR.drawable.ic_notification),
                    contentDescription = stringResource(id = homeR.string.notifications)
                )
            }
        },
        colors = LearnyscapeTopAppbarDefaults.homeTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LecturerHomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            LearnyscapeLogoText()
        },
        colors = LearnyscapeTopAppbarDefaults.homeTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onPendingClassRequestClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = searchR.string.available_class),
        actionsIcon = {
            StudentOnlyComposable {
                IconButton(onClick = onPendingClassRequestClick) {
                    Icon(
                        painter = painterResource(id = searchR.drawable.ic_hourglass),
                        contentDescription = stringResource(
                            id = searchR.string.pending_request_icon_description
                        )
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultHomeNavigatorTopAppBar(
    titleId: Int,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = titleId),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}


@Composable
private fun HomeNavigatorBottomBar(
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
                        painter = painterResource(id = destination.iconId),
                        contentDescription = null
                    )
                },
            )
        }
    }
}

@Composable
private fun NavDestination?.isDestinationInHierarchy(destination: HomeDestination) =
    this?.hierarchy?.any {
        it.route?.equals(destination.route, true) ?: false
    } ?: false
