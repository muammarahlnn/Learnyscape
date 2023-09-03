package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionScreen, 25/08/2023 17.38 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizSessionRoute(
    modifier: Modifier = Modifier,
    viewModel: QuizSessionViewModel = hiltViewModel()
) {
    QuizSessionScreen(
        quizName = viewModel.quizName,
        quizDuration = viewModel.quizDuration,
        questions = viewModel.questions,
        selectedOptionLetters = viewModel.selectedOptionLetters,
        modifier = modifier,
    )
}

@Composable
private fun QuizSessionScreen(
    quizName: String,
    quizDuration: Int,
    questions: List<MultipleChoiceQuestion>,
    selectedOptionLetters: SnapshotStateList<OptionLetter>,
    modifier: Modifier = Modifier,
) {
    var topAppBarHeightPx by remember { mutableFloatStateOf(0f) }
    var topAppBarOffsetHeightPx by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Updates the toolbar offset based on the scroll to enable collapsible behaviour
                val newOffset = topAppBarOffsetHeightPx + available.y
                topAppBarOffsetHeightPx = newOffset.coerceIn(-topAppBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }
    Box(modifier = modifier
        .fillMaxSize()
        .nestedScroll(nestedScrollConnection)
    ) {
        QuizSessionContent(
            paddingTop = topAppBarHeightPx,
            questions = questions,
            selectedOptionLetters = selectedOptionLetters,
        )
        QuizSessionTopAppBar(
            quizName = quizName,
            quizDuration = quizDuration,
            topAppBarOffsetHeightPx = topAppBarOffsetHeightPx,
            onGloballyPositioned = { topAppBarHeight ->
                topAppBarHeightPx = topAppBarHeight
            }
        )
    }
}

@Composable
private fun QuizSessionContent(
    paddingTop: Float,
    questions: List<MultipleChoiceQuestion>,
    selectedOptionLetters: SnapshotStateList<OptionLetter>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = with(LocalDensity.current) {
                paddingTop.toDp()
            },
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
        ),
        modifier = modifier,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        itemsIndexed(
            items = questions,
            key = { _, item ->
                item.id
            }
        ) { index, question ->
            QuestionItem(
                number = index + 1,
                question = question,
                selectedOptionLetter = selectedOptionLetters[index],
                onOptionSelect = { optionLetter ->
                    selectedOptionLetters[index] = optionLetter
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun QuestionItem(
    number: Int,
    question: MultipleChoiceQuestion,
    selectedOptionLetter: OptionLetter,
    onOptionSelect: (OptionLetter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        QuestionNumber(number = number)

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            QuestionText(question = question.question)
            
            Spacer(modifier = Modifier.height(10.dp))

            QuestionMultipleChoice(
                options = question.options,
                currentSelectedOptionLetter = selectedOptionLetter,
                onOptionSelect = onOptionSelect
            )
        }
    }
}

@Composable
private fun QuestionNumber(number: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = CircleShape,
            )
            .background(MaterialTheme.colorScheme.onPrimary)
            .clip(CircleShape)
            .size(30.dp)
            .padding(5.dp)
    ) {
        Text(
            text = "$number",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun QuestionText(
    question: String,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = question,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun QuestionMultipleChoice(
    options: List<Option>,
    currentSelectedOptionLetter: OptionLetter,
    onOptionSelect: (OptionLetter) -> Unit,
) {
    options.forEach { option ->
        val isSelected = currentSelectedOptionLetter == option.letter
        // TODO: Animated this colors
        val (backgroundColor, textColor) = if (isSelected) {
            MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onPrimary to MaterialTheme.colorScheme.onBackground
        }

        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onOptionSelect(option.letter)
                }
        ) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "${option.letter.name}. ",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = textColor,
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = option.text,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
    }
}

data class MultipleChoiceQuestion(
    val id: Int,
    val question: String,
    val options: List<Option>
)

data class Option(
    val letter: OptionLetter,
    val text: String
)

enum class OptionLetter {
    A, B, C, D, E,
    UNSELECTED,
}

