package com.muammarahlnn.learnyscape.feature.search

import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import com.muammarahlnn.learnyscape.core.model.data.DayModel
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.SearchTextField
import com.muammarahlnn.learnyscape.core.ui.util.StudentOnlyComposable
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import kotlinx.datetime.LocalTime


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchScreen, 20/07/2023 22.06 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SearchRoute(
    onPendingClassRequestClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(SearchContract.Event.FetchAvailableClasses)
    }

    val context = LocalContext.current
    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            is SearchContract.Effect.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val (refreshing, pullRefreshState) = use(refreshProvider = viewModel) {
        event(SearchContract.Event.FetchAvailableClasses)
    }

    SearchScreen(
        state = state,
        pullRefreshState = pullRefreshState,
        refreshing = refreshing,
        onRefresh = {
            event(SearchContract.Event.FetchAvailableClasses)
        },
        onPendingClassRequestClick = onPendingClassRequestClick,
        onSearchQueryChanged = { searchQuery ->
            event(SearchContract.Event.OnSearchQueryChanged(searchQuery))
        },
        onClassItemClick = { availableClass ->
            event(SearchContract.Event.OnAvailableClassClick(availableClass))
        },
        onRequestJoinRequestDialog = {
            event(SearchContract.Event.OnRequestJoinClass)
        },
        onDismissJoinRequestDialog = {
            event(SearchContract.Event.OnDismissJoinClass)
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun SearchScreen(
    state: SearchContract.State,
    pullRefreshState: PullRefreshState,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onPendingClassRequestClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onClassItemClick: (AvailableClassModel) -> Unit,
    onRequestJoinRequestDialog: () -> Unit,
    onDismissJoinRequestDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.showJoinRequestDialog) {
        // double bang operator used because it's guaranteed selectedAvailableClass will never be null
        JoinRequestClassDialog(
            loading = state.joinRequestClassDialogLoading,
            className = state.selectedAvailableClass?.name.orEmpty(),
            onRequest = onRequestJoinRequestDialog,
            onDismiss = onDismissJoinRequestDialog,
        )
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            SearchTopAppBar(
                scrollBehavior = scrollBehavior,
                onPendingClassRequestClick = onPendingClassRequestClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            when (state.uiState) {
                SearchContract.UiState.Loading -> {
                    SearchContentLoading()
                }

                is SearchContract.UiState.Success -> {
                    SearchContent(
                        availableClasses = state.uiState.availableClasses,
                        searchQuery = state.searchQuery,
                        onSearchQueryChanged = onSearchQueryChanged,
                        onClassItemClick = onClassItemClick,
                        modifier = modifier.fillMaxSize()
                    )
                }

                SearchContract.UiState.SuccessEmpty -> {
                    NoDataScreen(
                        text = stringResource(id = R.string.empty_classes_text),
                        modifier = modifier.fillMaxSize()
                    )
                }

                is SearchContract.UiState.NoInternet -> {
                    NoInternetScreen(
                        text = state.uiState.message,
                        onRefresh = onRefresh,
                        modifier = modifier.fillMaxSize()
                    )
                }

                is SearchContract.UiState.Error -> {
                    ErrorScreen(
                        text = state.uiState.message,
                        onRefresh = onRefresh,
                        modifier = modifier.fillMaxSize()
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun SearchContentLoading(
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

        Spacer(modifier = Modifier.height(16.dp))

        repeat(4) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SearchContent(
    availableClasses: List<AvailableClassModel>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onClassItemClick: (AvailableClassModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchTextField(
            searchQuery = searchQuery,
            placeholderText = stringResource(
                id = R.string.search_available_class_placeholder
            ),
            onSearchQueryChanged = onSearchQueryChanged,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 12.dp,
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = availableClasses,
                key = { it.id },
            ) { availableClass ->
                SearchedClassCard(
                    onClassClick = onClassItemClick,
                    availableClass = availableClass,
                )
            }
        }
    }
}

@Composable
private fun SearchedClassCard(
    availableClass: AvailableClassModel,
    onClassClick: (AvailableClassModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.clickable {
            onClassClick(availableClass)
        }
    ) {
        Column{
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_school),
                    contentDescription = stringResource(id = R.string.available_class),
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(64.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
            ) {
                Text(
                    text = availableClass.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                availableClass.lecturers.forEach { lecturer ->
                    Text(
                        text = lecturer.fullName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 10.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = createClassScheduleDateText(
                        day = availableClass.day,
                        startTime = availableClass.startTime,
                        endTime = availableClass.endTime,
                    ),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun JoinRequestClassDialog(
    loading: Boolean,
    className: String,
    onRequest: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!loading) {
        BaseAlertDialog(
            title = stringResource(id = R.string.join_request_dialog_title),
            dialogText = stringResource(
                R.string.join_request_dialog_text,
                className
            ),
            onConfirm = onRequest,
            onDismiss = onDismiss,
            confirmText = stringResource(
                id = R.string.join_request_dialog_confirm_button_text
            ),
            modifier = modifier,
        )
    } else {
        AlertDialog(
            onDismissRequest = {
                // prevent user from dismissing the dialog when loading
            },
            confirmButton = {
                // there are no confirm button when loading
            },
            text = {
                // add padding top to make the loading well-centered due to empty confirmButton
                LoadingScreen(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 16.dp)
                )
            },
            shape = RoundedCornerShape(8.dp),
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier,
        )
    }
}

private fun createClassScheduleDateText(
    day: DayModel,
    startTime: LocalTime,
    endTime: LocalTime,
): String {
    return String.format(
        "%s, %02d:%02d - %02d:%02d",
        day.displayedText,
        startTime.hour, startTime.minute,
        endTime.hour, endTime.minute,
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
        title = stringResource(id = R.string.available_class),
        actionsIcon = {
            StudentOnlyComposable {
                IconButton(onClick = onPendingClassRequestClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hourglass),
                        contentDescription = stringResource(id = R.string.pending_request_icon_description)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}