package com.muammarahlnn.learnyscape.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.muammarahlnn.learnyscape.core.ui.ResourceClassScreen
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.use


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizScreen, 03/08/2023 15.47 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizRoute(
    classId: String,
    navigateBack: () -> Unit,
    navigateToResourceDetails: (Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(QuizContract.Event.SetClassId(classId))
    }
    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            QuizContract.Effect.NavigateBack ->
                navigateBack()

            is QuizContract.Effect.NavigateToResourceDetails ->
                navigateToResourceDetails(it.resourceTypeOrdinal)

            is QuizContract.Effect.NavigateToResourceCreate ->
                navigateToResourceCreate(it.classId, it.resourceTypeOrdinal)
        }
    }
    QuizScreen(
        event = { event(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreen(
    event: (QuizContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.quiz),
        onBackClick = { event(QuizContract.Event.OnNavigateBack) },
        onCreateNewResourceClick = { event(QuizContract.Event.OnNavigateToResourceCreate) },
        modifier = modifier,
    ) { paddingValues, scrollBehavior ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(paddingValues),
        ) {
            repeat(20) {
                item {
                    ClassResourceCard(
                        classResourceType = ClassResourceType.QUIZ,
                        title = "Quiz Local Data Persistent dan Database",
                        timeLabel = "Start at 21 May 2023, 21:21",
                        onItemClick = { event(QuizContract.Event.OnNavigateToResourceDetails) },
                    )
                }
            }
        }
    }
}