package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizResourceContent, 28/12/2023 01.46
 */
@Composable
internal fun QuizResourceContent(
    state: ResourceCreateContract.State,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuizTypeClick: () -> Unit,
    onQuestionsClick: () -> Unit,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    onDurationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TitleInputCard(
            resourceType = state.resourceType,
            title = state.title,
            onTitleChange = onTitleChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        DescriptionInputCard(
            resourceType = state.resourceType,
            description = state.description,
            onDescriptionChange = onDescriptionChange,
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuizTypeInputCard(
            quizType = state.quizType,
            onQuizTypeClick = onQuizTypeClick,
            onQuestionsClick = onQuestionsClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        StartEndDateInputCard(
            startDate = state.startDate,
            endDate = state.endDate,
            onStartDateClick = onStartDateClick,
            onEndDateClick = onEndDateClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DurationInputCard(
            duration = state.duration,
            onDurationClick = onDurationClick,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}