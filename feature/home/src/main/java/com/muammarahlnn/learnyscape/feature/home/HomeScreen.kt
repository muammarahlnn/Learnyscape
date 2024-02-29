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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.model.data.UserRole
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LearnyscapeLogoText
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.SearchTextField
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Event
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Navigation
import com.muammarahlnn.learnyscape.feature.home.HomeContract.State
import com.muammarahlnn.learnyscape.feature.home.HomeContract.UiState
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeScreen, 20/07/2023 19.56 by Muammar Ahlan Abimanyu
 */
@Composable
internal fun HomeRoute(
    controller: HomeController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            is Navigation.NavigateToClass ->
                controller.navigateToClass(navigation.classId)

            Navigation.NavigateToNotifications ->
                controller.navigateToNotifications()
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(Event.FetchEnrolledClasses)
    }

    HomeScreen(
        state = state,
        refreshState = use(viewModel) { event(Event.FetchEnrolledClasses) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
    state: State,
    refreshState: RefreshState,
    event: (Event) -> Unit,
    navigate: (Navigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            HomeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNotificationsClick = { navigate(Navigation.NavigateToNotifications) },
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

                UiState.SuccessEmpty -> EmptyClassesContent(
                    modifier = Modifier.fillMaxSize(),
                )

                is UiState.Success -> HomeContent(
                    searchQuery = state.searchQuery,
                    isSearching = state.isSearching,
                    classes = state.searchedClasses,
                    onSearchQueryChanged = { event(Event.OnSearchQueryChanged(it)) },
                    onClassClick = { navigate(Navigation.NavigateToClass(it)) },
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
    isSearching: Boolean,
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

        val contentModifier = Modifier.weight(1f)
        if (isSearching) {
            LoadingScreen(modifier = contentModifier)
        } else {
            if (classes.isEmpty()) {
                SearchNotFoundContent(
                    searchedClass = searchQuery,
                    modifier = contentModifier.then(
                        Modifier.fillMaxWidth()
                    ),
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = contentModifier
                ) {
                    items(
                        items = classes,
                        key = { it.id },
                    ) { enrolledClass ->
                        ClassCard(
                            classId = enrolledClass.id,
                            className = enrolledClass.className,
                            lecturerName = enrolledClass.lecturerNames.first(),
                            onClassClick = onClassClick,
                        )
                    }
                }
            }
        }

//        LazyColumn(
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier.weight(1f)
//        ) {
//            if (!isSearching) {
//                if (classes.isNotEmpty()) {
//                    items(
//                        items = classes,
//                        key = {
//                            it.id
//                        }
//                    ) { classInfo ->
//                        ClassCard(
//                            classId = classInfo.id,
//                            className = classInfo.className,
//                            lecturerName = classInfo.lecturerNames.first(),
//                            onClassClick = onClassClick,
//                        )
//                    }
//                } else {
//                    item {
//                        SearchNotFoundContent(
//                            searchedClass = searchQuery,
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    }
//                }
//            } else {
//                item {
//                    LoadingScreen(
//                        modifier = Modifier.fillMaxSize()
//                    )
//                }
//            }
//        }
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
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = designSystemR.drawable.no_data_illustration),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.empty_class_illustration_desc),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun SearchNotFoundContent(
    searchedClass: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = designSystemR.drawable.no_data_illustration),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(
                id = R.string.search_not_found_desc,
                searchedClass,
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
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