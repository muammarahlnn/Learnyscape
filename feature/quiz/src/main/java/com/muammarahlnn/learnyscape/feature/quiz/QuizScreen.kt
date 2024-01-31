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
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import kotlinx.coroutines.launch


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizScreen, 03/08/2023 15.47 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizRoute(
    classId: String,
    navigateBack: () -> Unit,
    navigateToResourceDetails: (String, Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        launch {
            event(QuizContract.Event.SetClassId(classId))
        }.join()

        launch {
            event(QuizContract.Event.FetchQuizzes)
        }
    }

    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            QuizContract.Effect.NavigateBack ->
                navigateBack()

            is QuizContract.Effect.NavigateToResourceDetails ->
                navigateToResourceDetails(
                    it.resourceId,
                    it.resourceTypeOrdinal
                )

            is QuizContract.Effect.NavigateToResourceCreate ->
                navigateToResourceCreate(it.classId, it.resourceTypeOrdinal)
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(QuizContract.Event.FetchQuizzes)
    }

    QuizScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun QuizScreen(
    state: QuizContract.State,
    refreshState: RefreshState,
    event: (QuizContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.quiz),
        onBackClick = { event(QuizContract.Event.OnNavigateBack) },
        onCreateNewResourceClick = { event(QuizContract.Event.OnNavigateToResourceCreate) },
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
                            onItemClick = { event(QuizContract.Event.OnNavigateToResourceDetails(quiz.id)) }
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