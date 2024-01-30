package com.muammarahlnn.learnyscape.feature.assignment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.NoDataScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.ResourceClassScreen
import com.muammarahlnn.learnyscape.core.ui.ResourceScreenLoading
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import kotlinx.coroutines.launch


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AssignmentScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun AssignmentRoute(
    classId: String,
    navigateBack: () -> Unit,
    navigateToResourceDetails: (String, Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AssignmentViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        launch {
            event(AssignmentContract.Event.SetClassId(classId))
        }.join()

        launch {
            event(AssignmentContract.Event.FetchAssignments)
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(AssignmentContract.Event.FetchAssignments)
    }

    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            AssignmentContract.Effect.NavigateBack ->
                navigateBack()

            is AssignmentContract.Effect.NavigateToResourceDetails ->
                navigateToResourceDetails(
                    it.resourceId,
                    it.resourceTypeOrdinal,
                )

            is AssignmentContract.Effect.NavigateToResourceCreate ->
                navigateToResourceCreate(it.classId, it.resourceTypeOrdinal)
        }
    }

    AssignmentScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun AssignmentScreen(
    state: AssignmentContract.State,
    refreshState: RefreshState,
    event: (AssignmentContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.assignment),
        onBackClick = { event(AssignmentContract.Event.OnNavigateBack) },
        onCreateNewResourceClick = { event(AssignmentContract.Event.OnNavigateToResourceCreate) },
        modifier = modifier,
    ) { paddingValues, scrollBehavior ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state.uiState) {
                AssignmentUiState.Loading -> ResourceScreenLoading()

                is AssignmentUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                    ) {
                        items(
                            items = state.uiState.assignments,
                            key = { it.id }
                        ) { assignment ->
                            ClassResourceCard(
                                classResourceType = ClassResourceType.ASSIGNMENT,
                                title = assignment.name,
                                timeLabel = assignment.dueDate,
                                onItemClick = {
                                    event(AssignmentContract.Event.OnNavigateToResourceDetails(assignment.id))
                                },
                            )
                        }
                    }
                }

                AssignmentUiState.SuccessEmpty -> NoDataScreen(
                    text = stringResource(id = R.string.assignment),
                    modifier = Modifier.fillMaxSize()
                )

                is AssignmentUiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(AssignmentContract.Event.FetchAssignments) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}