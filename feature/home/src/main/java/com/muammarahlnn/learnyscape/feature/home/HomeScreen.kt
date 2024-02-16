package com.muammarahlnn.learnyscape.feature.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.model.data.UserRole
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LearnyscapeLogoText
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.SearchTextField
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Effect
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Event
import com.muammarahlnn.learnyscape.feature.home.HomeContract.State
import com.muammarahlnn.learnyscape.feature.home.HomeContract.UiState
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeScreen, 20/07/2023 19.56 by Muammar Ahlan Abimanyu
 */
@Composable
internal fun HomeRoute(
    navigateToNotifications: () -> Unit,
    navigateToClass: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(Event.FetchEnrolledClasses)
    }

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is Effect.NavigateToClass -> navigateToClass(effect.classId)
            Effect.NavigateToNotifications -> navigateToNotifications()
        }
    }

    HomeScreen(
        state = state,
        refreshState = use(viewModel) { event(Event.FetchEnrolledClasses) },
        event = { event(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
    state: State,
    refreshState: RefreshState,
    event: (Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            HomeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNotificationsClick = { event(Event.OnNotificationsClick) },
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state.uiState) {
                UiState.Loading -> HomeContentLoading()

                is UiState.NoInternet -> NoInternetScreen(
                    text = state.uiState.message,
                    onRefresh = { event(Event.FetchEnrolledClasses) },
                    modifier = Modifier.fillMaxSize()
                )

                is UiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(Event.FetchEnrolledClasses) },
                    modifier = Modifier.fillMaxSize()
                )

                UiState.SuccessEmptyClasses -> EmptyClassesContent(
                    modifier = Modifier,
                )

                is UiState.Success -> HomeContent(
                    searchQuery = state.searchQuery,
                    classes = state.uiState.classes,
                    onSearchQueryChanged = { event(Event.OnSearchQueryChanged(it)) },
                    onClassClick = { event(Event.OnClassClick(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    searchQuery: String,
    classes: List<EnrolledClassInfoModel>,
    onSearchQueryChanged: (String) -> Unit,
    onClassClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SearchTextField(
            searchQuery = searchQuery,
            placeholderText = stringResource(id = R.string.search_placeholder_text),
            onSearchQueryChanged = onSearchQueryChanged,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                )
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = classes,
                key = {
                    it.id
                }
            ) { classInfo ->
                ClassCard(
                    classId = classInfo.id,
                    className = classInfo.className,
                    lecturerName = classInfo.lecturerNames.first(),
                    onClassClick = onClassClick,
                )
            }
        }
    }
}

@Composable
private fun ClassCard(
    classId: String,
    className: String,
    lecturerName: String,
    onClassClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClassClick(classId)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = com.muammarahlnn.learnyscape.core.ui.R.drawable.ic_class),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = className,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = lecturerName,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun HomeContentLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(32.dp))

        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(16.dp))
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = designSystemR.drawable.no_data_illustration),
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
        UserRole.STUDENT -> {
            TopAppBar(
                title = {
                    LearnyscapeLogoText()
                },
                actions = {
                    IconButton(onClick = onNotificationsClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = stringResource(id = R.string.notifications)
                        )
                    }
                },
                colors = LearnyscapeTopAppbarDefaults.homeTopAppBarColors(),
                scrollBehavior = scrollBehavior,
                modifier = homeTopAppBarModifier,
            )
        }

        UserRole.LECTURER -> {
            CenterAlignedTopAppBar(
                title = {
                    LearnyscapeLogoText()
                },
                colors = LearnyscapeTopAppbarDefaults.homeTopAppBarColors(),
                scrollBehavior = scrollBehavior,
                modifier = homeTopAppBarModifier,
            )
        }

        UserRole.NOT_LOGGED_IN -> Unit
    }
}