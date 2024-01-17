package com.muammarahlnn.learnyscape.feature.module

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
 * @file ModuleScreen, 03/08/2023 15.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ModuleRoute(
    classId: String,
    navigateBack: () -> Unit,
    navigateToResourceDetails: (Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ModuleViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        launch {
            event(ModuleContract.Event.SetClassId(classId))
        }.join()

        launch {
            event(ModuleContract.Event.FetchModules)
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(ModuleContract.Event.FetchModules)
    }

    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            ModuleContract.Effect.NavigateBack ->
                navigateBack()

            is ModuleContract.Effect.NavigateToResourceDetails ->
                navigateToResourceDetails(it.resourceTypeOrdinal)

            is ModuleContract.Effect.NavigateToResourceCreate ->
                navigateToResourceCreate(it.classId, it.resourceTypeOrdinal)
        }
    }

    ModuleScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ModuleScreen(
    state: ModuleContract.State,
    refreshState: RefreshState,
    event: (ModuleContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.module),
        onBackClick = { event(ModuleContract.Event.OnNavigateBack) },
        onCreateNewResourceClick = { event(ModuleContract.Event.OnNavigateToResourceCreate) },
        modifier = modifier,
    ) { paddingValues, scrollBehavior ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state.uiState) {
                ModuleUiState.Loading -> ResourceScreenLoading()

                is ModuleUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                    ) {
                        items(
                            items = state.uiState.modules,
                            key = { it.id }
                        ) { module ->
                            ClassResourceCard(
                                classResourceType = ClassResourceType.MODULE,
                                title = module.name,
                                timeLabel = module.updatedAt
                            )
                        }
                    }
                }

                ModuleUiState.SuccessEmpty -> NoDataScreen(
                    text = stringResource(id = R.string.empty_modules),
                    modifier = Modifier.fillMaxSize()
                )

                is ModuleUiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(ModuleContract.Event.FetchModules) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}