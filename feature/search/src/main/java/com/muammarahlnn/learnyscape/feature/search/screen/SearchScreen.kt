package com.muammarahlnn.learnyscape.feature.search.screen

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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import com.muammarahlnn.learnyscape.core.model.data.DayModel
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.SearchTextField
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.feature.search.R
import com.muammarahlnn.learnyscape.feature.search.uistate.JoinRequestClassDialogUiState
import com.muammarahlnn.learnyscape.feature.search.uistate.SearchUiState
import com.muammarahlnn.learnyscape.feature.search.viewmodel.SearchViewModel
import kotlinx.datetime.LocalTime


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchScreen, 20/07/2023 22.06 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun SearchRoute(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = viewModel::fetchAvailableClasses
    )

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val joinRequestDialogUiState by viewModel.joinRequestDialogUiState.collectAsStateWithLifecycle()
    SearchScreen(
        uiState = uiState,
        scrollBehavior = scrollBehavior,
        pullRefreshState = pullRefreshState,
        isRefreshing = refreshing,
        joinRequestDialogUiState = joinRequestDialogUiState,
        searchQuery = searchQuery,
        showJoinRequestDialog = viewModel.showJoinRequestDialog,
        onRefresh = viewModel::fetchAvailableClasses,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        selectedAvailableClass = viewModel.selectedAvailableClass,
        onClassItemClick = viewModel::onAvailableClassClick,
        onRequestJoinRequestDialog = viewModel::requestJoinClass,
        onDismissJoinRequestDialog = viewModel::onDismissJoinRequestDialog,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    joinRequestDialogUiState: JoinRequestClassDialogUiState,
    showJoinRequestDialog: Boolean,
    onRefresh: () -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    selectedAvailableClass: AvailableClassModel?,
    onClassItemClick: (AvailableClassModel) -> Unit,
    onRequestJoinRequestDialog: () -> Unit,
    onDismissJoinRequestDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showJoinRequestDialog) {
        // double bang operator used because it's guaranteed selectedAvailableClass will never be null
        JoinRequestClassDialog(
            uiState = joinRequestDialogUiState,
            className = selectedAvailableClass?.name!!,
            onRequest = onRequestJoinRequestDialog,
            onDismiss = onDismissJoinRequestDialog,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        when (uiState) {
            SearchUiState.Loading -> {
                SearchContentLoading()
            }

            is SearchUiState.Success -> {
                SearchContent(
                    scrollBehavior = scrollBehavior,
                    availableClasses = uiState.availableClasses,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = onSearchQueryChanged,
                    onClassItemClick = onClassItemClick,
                    modifier = modifier.fillMaxSize()
                )
            }

            SearchUiState.SuccessEmpty -> {
                NoDataScreen(
                    text = stringResource(id = R.string.empty_classes_text),
                    modifier = modifier.fillMaxSize()
                )
            }

            is SearchUiState.NoInternet -> {
                NoInternetScreen(
                    text = uiState.message,
                    onRefresh = onRefresh,
                    modifier = modifier.fillMaxSize()
                )
            }

            is SearchUiState.Error -> {
                ErrorScreen(
                    text = uiState.message,
                    onRefresh = onRefresh,
                    modifier = modifier.fillMaxSize()
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchContent(
    scrollBehavior: TopAppBarScrollBehavior,
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
            modifier = Modifier
                .weight(1f)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
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
    uiState: JoinRequestClassDialogUiState,
    className: String,
    onRequest: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when (uiState) {
        JoinRequestClassDialogUiState.None -> {
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
        }

        JoinRequestClassDialogUiState.Loading -> {
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

        JoinRequestClassDialogUiState.Success -> {
            Toast.makeText(
                context, 
                stringResource(
                    id = R.string.success_request_join_class,
                    className
                ), 
                Toast.LENGTH_SHORT
            ).show()
            onDismiss()
        }

        is JoinRequestClassDialogUiState.NoInternet -> {
            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            onDismiss()
        }

        is JoinRequestClassDialogUiState.Error -> {
            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            onDismiss()
        }
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