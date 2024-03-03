package com.muammarahlnn.learnyscape.feature.pendingrequest

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file PendingRequestScreen, 10/10/2023 17.13 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun PendingRequestRoute(
    controller: PendingRequestController,
    modifier: Modifier = Modifier,
    viewModel: PendingRequestViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            PendingRequestNavigation.NavigateBack ->
                controller.navigateBack()
        }
    }

    val (state, event) = use(viewModel)
    LaunchedEffect(Unit) {
        event(PendingRequestContract.Event.FetchPendingRequestClasses)
    }
    

    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is PendingRequestContract.Effect.ShowToast ->
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
        }
    }

    PendingRequestScreen(
        state = state,
        refreshState = use(viewModel) { event(PendingRequestContract.Event.FetchPendingRequestClasses) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun PendingRequestScreen(
    state: PendingRequestContract.State,
    refreshState: RefreshState,
    event: (PendingRequestContract.Event) -> Unit,
    navigate: (PendingRequestNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.showCancelRequestClassDialog) {
        state.selectedPendingRequest?.let {
            CancelPendingRequestDialog(
                className = it.className,
                onConfirm = { event(PendingRequestContract.Event.OnCancelPendingRequestClass)},
                onDismiss = { event(PendingRequestContract.Event.OnDismissCancelRequestClass) },
            )
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            PendingRequestTopAppBar(
                onBackClick = { navigate(PendingRequestNavigation.NavigateBack) },
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

        when (state.uiState) {
            PendingRequestContract.UiState.Loading -> PendingRequestLoadingContent()
            
            is PendingRequestContract.UiState.Error -> ErrorScreen(
                text = state.uiState.message,
                onRefresh = { event(PendingRequestContract.Event.FetchPendingRequestClasses) },
                modifier = contentModifier,
            )
            
            PendingRequestContract.UiState.SuccessEmpty -> NoDataScreen(
                text = stringResource(id = R.string.empty_pending_request_desc),
                modifier = contentModifier,
            )

            is PendingRequestContract.UiState.Success -> PullRefreshScreen(
                pullRefreshState = refreshState.pullRefreshState,
                refreshing = refreshState.refreshing,
                modifier = contentModifier,
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    items(
                        items = state.pendingRequestClasses,
                        key = { it.id },
                    ) { pendingRequestClass ->
                        PendingClassRequestCard(
                            className = pendingRequestClass.className,
                            lecturerNames = pendingRequestClass.lecturerNames,
                            onClassClick = { event(PendingRequestContract.Event.OnSelectCancelRequestClass(pendingRequestClass)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PendingClassRequestCard(
    className: String,
    lecturerNames: List<String>,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
           verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = com.muammarahlnn.learnyscape.core.ui.R.drawable.ic_class),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // this is a hardcoded text just for dummy purpose
                Text(
                    text = className,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                lecturerNames.forEach {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            IconButton(onClick = onClassClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = stringResource(id = R.string.cancel_icon_description)
                )
            }
        }
    }
}

@Composable
private fun PendingRequestLoadingContent() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        repeat(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun CancelPendingRequestDialog(
    className: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.cancel_request_dialog_title),
        dialogText = stringResource(id = R.string.cancel_request_dialog_text, className),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PendingRequestTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.pending_class_request),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back_bold),
                    contentDescription = stringResource(
                        id = designSystemR.string.navigation_back_icon_description
                    ),
                )
            }
        },
        colors = LearnyscapeTopAppbarDefaults.defaultTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}