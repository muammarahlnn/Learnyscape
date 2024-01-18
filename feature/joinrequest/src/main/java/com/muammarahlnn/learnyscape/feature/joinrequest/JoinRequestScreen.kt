package com.muammarahlnn.learnyscape.feature.joinrequest

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestScreen, 16/12/2023 02.36
 */
@Composable
internal fun JoinRequestRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JoinRequestViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(JoinRequestContract.Event.FetchWaitingList)
    }

    val context = LocalContext.current
    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            JoinRequestContract.Effect.NavigateBack ->
                navigateBack()

            is JoinRequestContract.Effect.ShowToast ->
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(JoinRequestContract.Event.FetchWaitingList)
    }

    JoinRequestScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun JoinRequestScreen(
    state: JoinRequestContract.State,
    refreshState: RefreshState,
    event: (JoinRequestContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            JoinRequestTopAppBar(
                onBackClick = { event(JoinRequestContract.Event.OnCloseClick) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val contentModifier = Modifier.fillMaxSize()
            when (state.uiState) {
                JoinRequestUiState.Loading -> JoinRequestContentLoading(modifier = contentModifier)

                is JoinRequestUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = contentModifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                    ) {
                        items(
                            items = state.uiState.waitingList,
                            key = { it.id },
                        ) { waitingList ->
                            JoinRequestCard(
                                studentName = waitingList.fullName,
                                onRejectClick = {
                                    event(JoinRequestContract.Event.OnRejectStudent(waitingList.id))
                                },
                                onAcceptClick = {
                                    event(JoinRequestContract.Event.OnAcceptStudent(waitingList.id))
                                },
                            )
                        }
                    }
                }
                
                JoinRequestUiState.SuccessEmpty -> NoDataScreen(
                    text = stringResource(id = R.string.empty_waiting_list),
                    modifier = contentModifier,
                )
                
                is JoinRequestUiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(JoinRequestContract.Event.FetchWaitingList) }
                )
            }
        }
    }
}

@Composable
private fun JoinRequestContentLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        repeat(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun JoinRequestCard(
    studentName: String,
    onRejectClick: () -> Unit,
    onAcceptClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            PhotoProfileImage(
                photoProfile = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = studentName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            RejectButton(
                onClick = onRejectClick,
            )

            Spacer(modifier = Modifier.width(8.dp))

            AcceptButton(
                onClick = onAcceptClick,
            )
        }
    }
}

@Composable
private fun AcceptButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CircleButton(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = designSystemR.drawable.ic_check),
            contentDescription = stringResource(id = R.string.accept_request),
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(6.dp),
        )
    }
}

@Composable
private fun RejectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CircleButton(
        backgroundColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = designSystemR.drawable.ic_close),
            contentDescription = stringResource(id = R.string.reject_request),
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(6.dp),
        )
    }
}

@Composable
private fun CircleButton(
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .size(32.dp)
            .background(backgroundColor)
            .clickable { onClick() },
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JoinRequestTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.join_requests),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = designSystemR.string.navigation_back_icon_description),
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}