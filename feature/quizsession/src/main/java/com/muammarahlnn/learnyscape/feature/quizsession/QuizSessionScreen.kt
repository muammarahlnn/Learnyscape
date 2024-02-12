package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ErrorDialog
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingDialog
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.SuccessDialog
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.quizsession.composable.QuizSessionTopAppBar
import kotlin.math.roundToInt
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionScreen, 25/08/2023 17.38 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizSessionRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizSessionViewModel = hiltViewModel()
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(QuizSessionContract.Event.FetchQuizQuestions)
    }

    // prevent user to go back when pressed back
    BackHandler {
        event(QuizSessionContract.Event.ShowYouCanNotLeaveDialog(true))
    }

    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            QuizSessionContract.Effect.NavigateBack ->
                navigateBack()
        }
    }

    QuizSessionScreen(
        state = state,
        event = { event(it) },
        modifier = modifier,
    )
}

@Composable
private fun QuizSessionScreen(
    state: QuizSessionContract.State,
    event: (QuizSessionContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.overlayComposableVisibility.showSubmitAnswerDialog) {
        SubmitAnswerDialog(
            onConfirm = { event(QuizSessionContract.Event.OnSubmitAnswers) },
            onDismiss = { event(QuizSessionContract.Event.ShowSubmitAnswerDialog(false)) },
        )
    }

    if (state.overlayComposableVisibility.showUnansweredQuestionsDialog) {
        UnansweredQuestionsDialog(
            unansweredQuestions = state.unansweredQuestions,
            onDismiss = { event(QuizSessionContract.Event.ShowUnansweredQuestionsDialog(false)) }
        )
    }

    if (state.overlayComposableVisibility.showTimeoutDialog) {
        TimeoutDialog(
            onContinue = { event(QuizSessionContract.Event.OnQuizIsOver) },
        )
    }

    if (state.overlayComposableVisibility.showYouCanNotLeaveDialog) {
        YouCanNotLeaveDialog(
            onDismiss = { event(QuizSessionContract.Event.ShowYouCanNotLeaveDialog(false)) }
        )
    }

    if (state.overlayComposableVisibility.showSubmittingAnswersDialog) {
        SubmittingAnswersDialog(
            uiState = state.submittingAnswersDialogUiState,
            onContinue = { event(QuizSessionContract.Event.OnQuizIsOver) },
            onDismiss = { event(QuizSessionContract.Event.ShowSubmittingAnswersDialog(false)) }
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

    when (state.uiState) {
        QuizSessionContract.UiState.Loading -> LoadingScreen(
            modifier = Modifier.fillMaxSize()
        )

        is QuizSessionContract.UiState.Error -> ErrorScreen(
            text = state.uiState.message,
            onRefresh = { event(QuizSessionContract.Event.FetchQuizQuestions) },
            modifier = Modifier.fillMaxSize()
        )

        QuizSessionContract.UiState.Success -> Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            QuizSessionContent(
                state = state,
                topPadding = topAppBarHeightPx,
                bottomPadding = submitButtonHeightPx,
                onAtBottomList = { updatedIsAtBottomList ->
                    isAtBottomList = updatedIsAtBottomList
                },
                onOptionSelected = { index, option ->
                    event(QuizSessionContract.Event.OnOptionSelected(index, option))
                },
                modifier = Modifier.fillMaxSize()
            )

            val isSubmittingAnswers =
                state.submittingAnswersDialogUiState !is QuizSessionContract.SubmittingAnswersDialogUiState.None
            QuizSessionTopAppBar(
                quizName = state.quizName,
                quizDuration = state.quizDuration,
                topAppBarOffsetHeightPx = animateTopAppBarOffset,
                isSubmittingAnswers = isSubmittingAnswers,
                onGloballyPositioned = { topAppBarHeight ->
                    topAppBarHeightPx = topAppBarHeight
                },
                onTimeout = { event(QuizSessionContract.Event.ShowTimeoutDialog(true)) },
            )

            SubmitButton(
                buttonOffsetHeightPx = animateSubmitButtonOffset,
                onButtonClick = { event(QuizSessionContract.Event.ShowSubmitAnswerDialog(true)) },
                onButtonGloballyPositioned = { buttonHeight ->
                    submitButtonHeightPx = buttonHeight
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun QuizSessionContent(
    state: QuizSessionContract.State,
    topPadding: Float,
    bottomPadding: Float,
    onOptionSelected: (Int, OptionLetter) -> Unit,
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
            items = state.multipleChoiceQuestions,
            key = { index, _ ->
                index
            }
        ) { index, question ->
            when (state.quizType) {
                QuizType.MULTIPLE_CHOICE -> {
                    MultipleChoiceQuestion(
                        number = index + 1,
                        question = question,
                        selectedOptionLetter = state.multipleChoiceAnswers[index],
                        onOptionSelected = { option ->
                            onOptionSelected(index, option)
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

                QuizType.NONE -> Unit
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
    onOptionSelected: (OptionLetter) -> Unit,
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
            onOptionSelected = onOptionSelected
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
    onOptionSelected: (OptionLetter) -> Unit,
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
                    onOptionSelected(option.letter)
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
            Text(
                text = stringResource(id = R.string.submit),
                style = MaterialTheme.typography.labelMedium
            )
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
private fun YouCanNotLeaveDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.you_can_not_leave_dialog_title),
        dialogText = stringResource(id = R.string.you_can_not_leave_dialog_text),
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.ok),
        dismissText = null,
        modifier = modifier,
    )
}

@Composable
private fun UnansweredQuestionsDialog(
    unansweredQuestions: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.unanswered_dialog_title,),
        dialogText = stringResource(
            id = R.string.unanswered_dialog_text,
            unansweredQuestions,
        ),
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.ok),
        dismissText = null,
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

@Composable
private fun SubmittingAnswersDialog(
    uiState: QuizSessionContract.SubmittingAnswersDialogUiState,
    onContinue: () -> Unit,
    onDismiss: () -> Unit,
) {
    when (uiState) {
        QuizSessionContract.SubmittingAnswersDialogUiState.None -> Unit

        QuizSessionContract.SubmittingAnswersDialogUiState.Loading -> LoadingDialog()

        is QuizSessionContract.SubmittingAnswersDialogUiState.Success -> SuccessDialog(
            message = stringResource(id = R.string.submitting_answers_dialog_text),
            onConfirm = onContinue,
        )

        is QuizSessionContract.SubmittingAnswersDialogUiState.Error -> ErrorDialog(
            message = uiState.message,
            onDismiss = {
                onDismiss()
                onContinue()
            }
        )
    }
}