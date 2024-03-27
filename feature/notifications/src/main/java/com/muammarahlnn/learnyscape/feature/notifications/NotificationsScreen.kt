package com.muammarahlnn.learnyscape.feature.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedTypeModel.ANNOUNCEMENT
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedTypeModel.ASSIGNMENT
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedTypeModel.MODULE
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedTypeModel.QUIZ
import com.muammarahlnn.learnyscape.core.model.data.NotificationModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsContract.Event
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsContract.UiState
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NotificationsScreen, 01/08/2023 13.18 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun NotificationsRoute(
    controller: NotificationsController,
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            NotificationsNavigation.NavigateBack ->
                controller.navigateBack()

            is NotificationsNavigation.NavigateToResourceDetails ->
                controller.navigateToResourceDetails(
                    navigation.resourceId,
                    navigation.resourceTypeOrdinal
                )
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(Event.FetchNotifications)
    }

    NotificationsScreen(
        uiState = state,
        refreshState = use(refreshProvider = viewModel) { event(Event.FetchNotifications) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun NotificationsScreen(
    uiState: UiState,
    refreshState: RefreshState,
    event: (Event) -> Unit,
    navigate: (NotificationsNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            NotificationsTopAppBar(
                scrollBehavior = scrollBehavior,
                onBackClick = { navigate(NotificationsNavigation.NavigateBack) },
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NotificationsContent(
                uiState = uiState,
                scrollBehavior = scrollBehavior,
                onRefresh = { event(Event.FetchNotifications) },
                onNotificationClick = { resourceId, resourceTypeOrdinal ->
                    navigate(
                        NotificationsNavigation.NavigateToResourceDetails(
                            resourceId = resourceId,
                            resourceTypeOrdinal = resourceTypeOrdinal,
                        )
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationsContent(
    uiState: UiState,
    scrollBehavior: TopAppBarScrollBehavior,
    onRefresh: () -> Unit,
    onNotificationClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        UiState.Loading -> NotificationsLoadingScreen()

        is UiState.NoInternet -> NoInternetScreen(
            text = uiState.message,
            onRefresh = onRefresh,
            modifier = modifier,
        )

        is UiState.Error -> ErrorScreen(
            text = uiState.message,
            onRefresh = onRefresh,
            modifier = modifier,
        )

        UiState.SuccessEmpty -> NoDataScreen(
            text = stringResource(id = R.string.empty_notifications_desc),
            modifier = modifier,
        )

        is UiState.Success -> LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            items(
                items = uiState.notifications,
                key = { it.id },
            ) { notification ->
                NotificationItem(
                    notification = notification,
                    onNotificationClick = onNotificationClick
                )
            }
        }
    }
}

@Composable
private fun NotificationsLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        repeat(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationsTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.notifications),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                    contentDescription = stringResource(
                        id = designSystemR.string.navigation_back_icon_description,
                    ),
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@Composable
private fun NotificationItem(
    notification: NotificationModel,
    onNotificationClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val classResourceType = when (notification.type) {
        ANNOUNCEMENT -> ClassResourceType.ANNOUNCEMENT
        MODULE -> ClassResourceType.MODULE
        ASSIGNMENT -> ClassResourceType.ASSIGNMENT
        QUIZ -> ClassResourceType.QUIZ
    }

    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onNotificationClick(
                    notification.uri,
                    classResourceType.ordinal
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = classResourceType.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = notification.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = notification.createdAt.substringBefore(','),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                ),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
