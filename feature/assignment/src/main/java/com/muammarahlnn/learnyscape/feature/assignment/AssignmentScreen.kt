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
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.use
import kotlinx.coroutines.launch


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AssignmentScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun AssignmentRoute(
    classId: String,
    controller: AssignmentController,
    modifier: Modifier = Modifier,
    viewModel: AssignmentViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            AssignmentNavigation.NavigateBack ->
                controller.navigateBack()

            is AssignmentNavigation.NavigateToResourceCreate ->
                controller.navigateToResourceCreate(
                    navigation.classId,
                    navigation.resourceTypeOrdinal,
                )

            is AssignmentNavigation.NavigateToResourceDetails ->
                controller.navigateToResourceDetails(
                    navigation.classId,
                    navigation.resourceId,
                    navigation.resourceTypeOrdinal,
                )
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        launch {
            event(AssignmentContract.Event.SetClassId(classId))
        }.join()

        launch {
            event(AssignmentContract.Event.FetchAssignments)
        }
    }

    AssignmentScreen(
        state = state,
        refreshState = use(viewModel) { event(AssignmentContract.Event.FetchAssignments) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun AssignmentScreen(
    state: AssignmentContract.State,
    refreshState: RefreshState,
    event: (AssignmentContract.Event) -> Unit,
    navigate: (AssignmentNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.assignment),
        onBackClick = { navigate(AssignmentNavigation.NavigateBack) },
        onCreateNewResourceClick = {
            navigate(AssignmentNavigation.NavigateToResourceCreate(
                classId = state.classId,
                resourceTypeOrdinal = state.assignmentOrdinal,
            ))
        },
        modifier = modifier,
    ) { paddingValues, scrollBehavior ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state.uiState) {
                AssignmentContract.UiState.Loading -> ResourceScreenLoading()

                is AssignmentContract.UiState.Success -> {
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
                                    navigate(AssignmentNavigation.NavigateToResourceDetails(
                                        classId = state.classId,
                                        resourceId = assignment.id,
                                        resourceTypeOrdinal = it,
                                    ))
                                },
                            )
                        }
                    }
                }

                AssignmentContract.UiState.SuccessEmpty -> NoDataScreen(
                    text = stringResource(id = R.string.assignment),
                    modifier = Modifier.fillMaxSize()
                )

                is AssignmentContract.UiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(AssignmentContract.Event.FetchAssignments) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}