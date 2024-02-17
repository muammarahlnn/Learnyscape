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
import com.muammarahlnn.learnyscape.core.ui.LoadingDialog
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.SearchTextField
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.StudentOnlyComposable
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.search.SearchContract.Effect
import com.muammarahlnn.learnyscape.feature.search.SearchContract.Event
import com.muammarahlnn.learnyscape.feature.search.SearchContract.Navigation
import com.muammarahlnn.learnyscape.feature.search.SearchContract.State
import com.muammarahlnn.learnyscape.feature.search.SearchContract.UiState
import kotlinx.datetime.LocalTime


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchScreen, 20/07/2023 22.06 by Muammar Ahlan Abimanyu
 */
@Composable
internal fun SearchRoute(
    controller: SearchController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            Navigation.NavigateToPendingClass ->
                controller.navigateToPendingRequestClass()
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(Event.FetchAvailableClasses)
    }

    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is Effect.ShowToast ->
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
        }
    }

    SearchScreen(
        state = state,
        refreshState = use(viewModel) { event(Event.FetchAvailableClasses) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun SearchScreen(
    state: State,
    refreshState: RefreshState,
    event: (Event) -> Unit,
    navigate: (Navigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.showJoinRequestDialog) {
        JoinRequestClassDialog(
            loading = state.joinRequestClassDialogLoading,
            className = state.selectedAvailableClass?.name.orEmpty(),
            onRequest = { event(Event.OnRequestJoinClass) },
            onDismiss = { event(Event.OnDismissJoinClass) },
        )
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            SearchTopAppBar(
                scrollBehavior = scrollBehavior,
                onPendingClassRequestClick = { navigate(Navigation.NavigateToPendingClass) }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state.uiState) {
                UiState.Loading -> {
                    SearchContentLoading()
                }

                is UiState.Success -> {
                    SearchContent(
                        availableClasses = state.uiState.availableClasses,
                        searchQuery = state.searchQuery,
                        onSearchQueryChanged = { event(Event.OnSearchQueryChanged(it)) },
                        onClassItemClick = { event(Event.OnAvailableClassClick(it)) },
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    )
                }

                UiState.SuccessEmpty -> {
                    NoDataScreen(
                        text = stringResource(id = R.string.empty_classes_text),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is UiState.NoInternet -> {
                    NoInternetScreen(
                        text = state.uiState.message,
                        onRefresh = { event(Event.FetchAvailableClasses) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is UiState.Error -> {
                    ErrorScreen(
                        text = state.uiState.message,
                        onRefresh = { event(Event.FetchAvailableClasses) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
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
        LoadingDialog()
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