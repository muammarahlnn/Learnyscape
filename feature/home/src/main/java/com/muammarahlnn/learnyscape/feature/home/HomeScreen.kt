package com.muammarahlnn.learnyscape.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.model.data.UserRole
import com.muammarahlnn.learnyscape.core.ui.ClassCard
import com.muammarahlnn.learnyscape.core.ui.LearnyscapeText
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeScreen, 20/07/2023 19.56 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun HomeRoute(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onNotificationsClick = onNotificationsClick,
        onClassClick = onClassClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

        HomeTopAppBar(
            onNotificationsClick = onNotificationsClick,
            scrollBehavior = scrollBehavior,
        )

        HomeContent(
            uiState = uiState,
            scrollBehavior = scrollBehavior,
            onClassClick = onClassClick,
        )

        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        HomeUiState.Loading -> {
            LoadingScreen()
        }

        is HomeUiState.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                items(
                    items = uiState.classes,
                    key = {
                        it.id
                    }
                ) { classInfo ->
                    ClassCard(
                        className = classInfo.className,
                        lecturerName = classInfo.lecturerNames.first(),
                        onItemClick = onClassClick,
                    )
                }
            }
        }

        HomeUiState.SuccessEmptyClasses -> {
            EmptyClassesContent(
                modifier = modifier,
            )
        }

        is HomeUiState.Error -> {
            // TODO: Add onError content
        }

        is HomeUiState.NoInternet -> {
            NoInternetContent(
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun EmptyClassesContent(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_data_illustration),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.empty_class_illustration_desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

}

@Composable
private fun NoInternetContent(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi_off),
            contentDescription = stringResource(id = R.string.wifi_off_icon_desc),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.wifi_off_icon_desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val user = LocalUserModel.current
    when (user.role) {
        UserRole.STUDENT -> StudentHomeTopAppBar(
            onNotificationsClick = onNotificationsClick,
            modifier = modifier,
            scrollBehavior = scrollBehavior,
        )
        UserRole.LECTURER -> LecturerHomeTopApBar(
            modifier = modifier,
            scrollBehavior = scrollBehavior,
        )
        UserRole.NOT_LOGGED_IN -> {
            // no op
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentHomeTopAppBar(
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = {
            LearnyscapeText()
        },
        actionIconRes = R.drawable.ic_notification,
        actionIconContentDescription = stringResource(id = R.string.top_app_bar_navigation_icon_description),
        colors = LearnyscapeTopAppbarDefaults.homeTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        onActionClick = onNotificationsClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LecturerHomeTopApBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeCenterTopAppBar(
        title = {
            LearnyscapeText()
        },
        colors = LearnyscapeTopAppbarDefaults.homeTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}