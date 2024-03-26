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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.SearchNotFoundScreen
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
        state.selectedAvailableClass?.let {
            ActionDialog(
                title = stringResource(id = R.string.join_request_dialog_title),
                text = stringResource(id = R.string.join_request_dialog_text, it.name),
                confirmText = stringResource(id = R.string.join_request_dialog_confirm_button_text),
                loading = state.joinRequestClassDialogLoading,
                onConfirm = { event(Event.OnRequestJoinClass) },
                onDismiss = { event(Event.OnDismissJoinClass) },
            )
        }
    }

    if (state.showCancelRequestDialog) {
        state.selectedAvailableClass?.let {
            ActionDialog(
                title = stringResource(id = R.string.cancel_request_dialog_title),
                text = stringResource(id = R.string.cancel_request_dialog_text, it.name),
                confirmText = stringResource(id = R.string.cancel_request_dialog_confirm_button_text),
                dismissText = stringResource(id = R.string.cancel_request_dialog_dismiss_button_text),
                loading = state.cancelRequestClassDialogLoading,
                onConfirm = { event(Event.OnCancelRequestClass) },
                onDismiss = { event(Event.OnDismissCancelRequestClass) },
            )
        }
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
                        searchUiState = state.searchUiState,
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
    searchUiState: SearchContract.SearchUiState,
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

        val contentModifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        when (searchUiState) {
            SearchContract.SearchUiState.Loading -> LoadingScreen(
                modifier = contentModifier
            )

            is SearchContract.SearchUiState.Error -> ErrorScreen(
                text = searchUiState.message,
                onRefresh = {
                    onSearchQueryChanged(searchQuery)
                },
                modifier = contentModifier,
            )

            SearchContract.SearchUiState.SuccessEmpty -> SearchNotFoundScreen(
                searchedClass = searchQuery,
                modifier = contentModifier,
            )

            is SearchContract.SearchUiState.Success -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = contentModifier,
            ) {
                items(
                    items = searchUiState.availableClasses,
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
}

@Composable
private fun SearchedClassCard(
    availableClass: AvailableClassModel,
    onClassClick: (AvailableClassModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRequested = availableClass.requestStatus == AvailableClassModel.RequestStatus.PENDING
    BaseCard(
        modifier = modifier.clickable {
            onClassClick(availableClass)
        }
    ) {
        Column {
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

                if (isRequested) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(bottomStart = 8.dp),
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = stringResource(id = R.string.requested),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            )
                        )
                    }
                }
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
private fun ActionDialog(
    title: String,
    text: String,
    confirmText: String,
    loading: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    dismissText: String? = null,
) {
    if (!loading) {
        BaseAlertDialog(
            title = title,
            dialogText = text,
            onConfirm = onConfirm,
            onDismiss = onDismiss,
            confirmText = confirmText,
            dismissText = dismissText,
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