package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.ui.QuizType
import kotlin.math.roundToInt
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionScreen, 25/08/2023 17.38 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizSessionRoute(
    onQuizIsOver: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizSessionViewModel = hiltViewModel()
) {
    var showSubmitAnswerDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var showTimeoutDialog by rememberSaveable {
        mutableStateOf(false)
    }

    QuizSessionScreen(
        quizType = viewModel.quizType,
        quizName = viewModel.quizName,
        quizDuration = viewModel.quizDuration,
        questions = viewModel.questions,
        showSubmitAnswerDialog = showSubmitAnswerDialog,
        showTimeoutDialog = showTimeoutDialog,
        selectedOptionLetters = viewModel.selectedOptionLetters,
        onSubmitButtonClick = {
            showSubmitAnswerDialog = true
        },
        onConfirmSubmitAnswerDialog = {
            onQuizIsOver()
            showSubmitAnswerDialog = false
        },
        onDismissSubmitAnswerDialog = {
            showSubmitAnswerDialog = false
        },
        onTimeout = {
            showTimeoutDialog = true
        },
        onContinueTimeoutDialog = {
            onQuizIsOver()
            showTimeoutDialog = false
            showSubmitAnswerDialog = false
        },
        modifier = modifier,
    )
}

@Composable
private fun QuizSessionScreen(
    quizType: QuizType,
    quizName: String,
    quizDuration: Int,
    showSubmitAnswerDialog: Boolean,
    showTimeoutDialog: Boolean,
    questions: List<MultipleChoiceQuestion>,
    selectedOptionLetters: SnapshotStateList<OptionLetter>,
    onSubmitButtonClick: () -> Unit,
    onConfirmSubmitAnswerDialog: () -> Unit,
    onDismissSubmitAnswerDialog: () -> Unit,
    onTimeout: () -> Unit,
    onContinueTimeoutDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showSubmitAnswerDialog) {
        SubmitAnswerDialog(
            onConfirm = onConfirmSubmitAnswerDialog,
            onDismiss = onDismissSubmitAnswerDialog,
        )
    }

    if (showTimeoutDialog) {
        TimeoutDialog(
            onContinue = onContinueTimeoutDialog,
        )
    }

    var isAtBottomList by remember { mutableStateOf(false) }

    val collapsingAnimationSpec = SpringSpec<Float>(
        stiffness = Spring.StiffnessLow
    )
    var topAppBarHeightPx by remember { mutableFloatStateOf(0f) }
    var topAppBarOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    val animateTopAppBarOffset by animateFloatAsState(
        targetValue = topAppBarOffsetHeightPx,
        animationSpec = collapsingAnimationSpec,
        label = "Top app bar offset"
    )

    var submitButtonHeightPx by remember { mutableFloatStateOf(0f) }
    var submitButtonOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    val animateSubmitButtonOffset by animateFloatAsState(
        targetValue = if (!isAtBottomList) submitButtonOffsetHeightPx else 0f,
        animationSpec = collapsingAnimationSpec,
        label = "Submit button offset"
    )

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Updates the top app bar and submit offset
                // based on the scroll to enable collapsible behaviour
                val delta = available.y

                val newTopAppBarOffset = topAppBarOffsetHeightPx + delta
                topAppBarOffsetHeightPx = newTopAppBarOffset.coerceIn(-topAppBarHeightPx, 0f)

                val newSubmitButtonOffset = submitButtonOffsetHeightPx - delta
                submitButtonOffsetHeightPx = newSubmitButtonOffset.coerceIn(0f, submitButtonHeightPx)

                return Offset.Zero
            }
        }
    }
    Box(modifier = modifier
        .fillMaxSize()
        .nestedScroll(nestedScrollConnection)
    ) {
        QuizSessionContent(
            topPadding = topAppBarHeightPx,
            bottomPadding = submitButtonHeightPx,
            quizType = quizType,
            questions = questions,
            selectedOptionLetters = selectedOptionLetters,
            onAtBottomList = { updatedIsAtBottomList ->
                isAtBottomList = updatedIsAtBottomList
            },
        )
        QuizSessionTopAppBar(
            quizName = quizName,
            quizDuration = quizDuration,
            topAppBarOffsetHeightPx = animateTopAppBarOffset,
            onGloballyPositioned = { topAppBarHeight ->
                topAppBarHeightPx = topAppBarHeight
            },
            onTimeout = onTimeout,
        )
        SubmitButton(
            buttonOffsetHeightPx = animateSubmitButtonOffset,
            onButtonClick = onSubmitButtonClick,
            onButtonGloballyPositioned = { buttonHeight ->
                submitButtonHeightPx = buttonHeight
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun QuizSessionContent(
    topPadding: Float,
    bottomPadding: Float,
    quizType: QuizType,
    questions: List<MultipleChoiceQuestion>,
    selectedOptionLetters: SnapshotStateList<OptionLetter>,
    onAtBottomList: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val localDensity = LocalDensity.current

    val listState = rememberLazyListState()
    onAtBottomList(!listState.canScrollForward)

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            top = with(localDensity) {
                topPadding.toDp()
            },
            start = 16.dp,
            end = 16.dp,
            bottom = with(localDensity) {
                bottomPadding.toDp()
            },
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
            when (quizType) {
                QuizType.MULTIPLE_CHOICE_QUESTIONS -> {
                    MultipleChoiceQuestion(
                        number = index + 1,
                        question = question,
                        selectedOptionLetter = selectedOptionLetters[index],
                        onOptionSelect = { optionLetter ->
                            selectedOptionLetters[index] = optionLetter
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                QuizType.PHOTO_ANSWER -> {
                    PhotoAnswerQuestion(
                        number = index + 1,
                        question = question.question,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BaseQuestion(
    number: Int,
    question: String,
    modifier: Modifier = Modifier,
    answerContent: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        QuestionNumber(number = number)

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            QuestionText(question = question)

            Spacer(modifier = Modifier.height(10.dp))

            answerContent()
        }
    }
}

@Composable
private fun MultipleChoiceQuestion(
    number: Int,
    question: MultipleChoiceQuestion,
    selectedOptionLetter: OptionLetter,
    onOptionSelect: (OptionLetter) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseQuestion(
        number = number,
        question = question.question,
        modifier = modifier,
    ) {
        MultipleChoiceAnswer(
            options = question.options,
            currentSelectedOptionLetter = selectedOptionLetter,
            onOptionSelect = onOptionSelect
        )
    }
}

@Composable
private fun PhotoAnswerQuestion(
    number: Int,
    question: String,
    modifier: Modifier = Modifier,
) {
    BaseQuestion(
        number = number,
        question = question,
        modifier = modifier,
    ) {
        BaseCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_add),
                    contentDescription = stringResource(
                        id = designSystemR.string.add_icon_description
                    ),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(id = R.string.photo_answer_button_text),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
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
    BaseCard(
        modifier = Modifier.fillMaxWidth()
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
private fun MultipleChoiceAnswer(
    options: List<Option>,
    currentSelectedOptionLetter: OptionLetter,
    onOptionSelect: (OptionLetter) -> Unit,
) {
    options.forEach { option ->
        val isSelected = currentSelectedOptionLetter == option.letter
        val colorAnimationSpec: SpringSpec<Color> = SpringSpec(
            stiffness = Spring.StiffnessLow
        )
        val backgroundColor by animateColorAsState(
            targetValue = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimary
            },
            animationSpec = colorAnimationSpec,
            label = "Question multiple choice background color",
        )
        val textColor by animateColorAsState(
            targetValue = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            animationSpec = colorAnimationSpec,
            label = "Question multiple choice background color",
        )

        BaseCard(
            backgroundColor = backgroundColor,
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

@Composable
private fun SubmitButton(
    buttonOffsetHeightPx: Float,
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .offset {
                IntOffset(
                    x = 0,
                    y = buttonOffsetHeightPx.roundToInt()
                )
            }
            .onGloballyPositioned { layoutCoordinates ->
                onButtonGloballyPositioned(
                    layoutCoordinates.size.height.toFloat()
                )
            }
    ) {
        Button(
            onClick = onButtonClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp,
                ),
        ) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}

@Composable
private fun SubmitAnswerDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.submit_answer_dialog_title),
        dialogText = stringResource(id = R.string.submit_answer_dialog_text),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.submit),
        modifier = modifier,
    )
}

@Composable
private fun TimeoutDialog(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {
            // do nothing, to prevent user to dismiss the dialog 
            // by clicking outside the dialog or pressing the back button 
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_timer_off),
                contentDescription = stringResource(id = R.string.timeout_dialog_title),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.timeout_dialog_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.timeout_dialog_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onContinue
            ) {
                Text(
                    text = stringResource(id = R.string._continue),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
    )
}