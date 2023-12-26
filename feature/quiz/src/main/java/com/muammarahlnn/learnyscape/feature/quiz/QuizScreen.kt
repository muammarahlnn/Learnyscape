package com.muammarahlnn.learnyscape.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    onQuizClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuizScreen(
        onQuizClick = onQuizClick,
        onAddQuizClick = {},
        modifier = modifier,
    )
}

@Composable
private fun QuizScreen(
    onQuizClick: (Int) -> Unit,
    onAddQuizClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        onCreateNewResourceClick = onAddQuizClick,
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(paddingValues),
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