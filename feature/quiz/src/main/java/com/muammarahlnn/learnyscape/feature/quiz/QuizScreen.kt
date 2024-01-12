package com.muammarahlnn.learnyscape.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ResourceClassScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizScreen, 03/08/2023 15.47 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizRoute(
    onBackClick: () -> Unit,
    onQuizClick: (Int) -> Unit,
    onCreateNewQuizClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuizScreen(
        onBackClick = onBackClick,
        onQuizClick = onQuizClick,
        onCreateNewQuizClick = onCreateNewQuizClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreen(
    onBackClick: () -> Unit,
    onQuizClick: (Int) -> Unit,
    onCreateNewQuizClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        resourceTitle = stringResource(id = R.string.quiz),
        onBackClick = onBackClick,
        onCreateNewResourceClick = {
            onCreateNewQuizClick(ClassResourceType.QUIZ.ordinal)
        },
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
                        onItemClick = onQuizClick,
                    )
                }
            }
        }
    }
}