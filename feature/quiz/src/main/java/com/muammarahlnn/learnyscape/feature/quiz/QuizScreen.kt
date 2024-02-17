package com.muammarahlnn.learnyscape.feature.quiz

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
 * @file QuizScreen, 03/08/2023 15.47 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizRoute(
    classId: String,
    controller: QuizController,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            QuizNavigation.NavigateBack ->
                controller.navigateBack

            is QuizNavigation.NavigateToResourceCreate ->
                controller.navigateToResourceCreate(
                    navigation.classId,
                    navigation.resourceTypeOrdinal,
                )

            is QuizNavigation.NavigateToResourceDetails ->
                controller.navigateToResourceDetails(
                    navigation.resourceId,
                    navigation.resourceTypeOrdinal,
                )
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        launch {
            event(QuizContract.Event.SetClassId(classId))
        }.join()

        launch {
            event(QuizContract.Event.FetchQuizzes)
        }
    }

    QuizScreen(
        state = state,
        refreshState = use(viewModel) { event(QuizContract.Event.FetchQuizzes) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun QuizScreen(
    state: QuizContract.State,
    refreshState: RefreshState,
    event: (QuizContract.Event) -> Unit,
    navigate: (QuizNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.quiz),
        onBackClick = { navigate(QuizNavigation.NavigateBack) },
        onCreateNewResourceClick = {
            navigate(QuizNavigation.NavigateToResourceCreate(
                classId = state.classId,
                resourceTypeOrdinal = state.moduleOrdinal,
            ))
        },
        modifier = modifier,
    ) { paddingValues, scrollBehavior ->
        PullRefreshScreen(
            pullRefreshState =refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state.uiState) {
                QuizContract.UiState.Loading -> ResourceScreenLoading()

                is QuizContract.UiState.Success -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                ) {
                    items(
                        items = state.uiState.quizzes,
                        key = { it.id }
                    ) { quiz ->
                        ClassResourceCard(
                            classResourceType = ClassResourceType.QUIZ,
                            title = quiz.name,
                            timeLabel = quiz.startDate,
                            onItemClick = {
                                navigate(QuizNavigation.NavigateToResourceDetails(
                                    resourceId = quiz.id,
                                    resourceTypeOrdinal = it,
                                ))
                            }
                        )
                    }
                }
                
                QuizContract.UiState.SuccessEmpty -> NoDataScreen(
                    text = stringResource(id = R.string.empty_quizzes) 
                )
                
                is QuizContract.UiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(QuizContract.Event.FetchQuizzes) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}