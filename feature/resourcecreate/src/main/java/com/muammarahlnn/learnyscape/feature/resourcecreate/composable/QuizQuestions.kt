package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.TransparentTextField
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import kotlinx.coroutines.launch
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizQuestionsScreen, 02/01/2024 10.25
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun QuizQuestions(
    quizType: QuizType,
    multipleChoiceQuestions: List<MultipleChoiceQuestion>,
    photoAnswerQuestions: List<PhotoAnswerQuestion>,
    onCloseClick: () -> Unit,
    onUnfilledQuestionsExists: () -> Unit,
    onSaveQuestions: (List<MultipleChoiceQuestion>, List<PhotoAnswerQuestion>) -> Unit,
    modifier: Modifier = Modifier,
    state: QuizQuestionsState = rememberQuizQuestionQuestionsState(
        quizType = quizType,
        multipleChoiceQuestions = multipleChoiceQuestions,
        photoAnswerQuestions = photoAnswerQuestions,
    )
) {
    LaunchedEffect(Unit) {
        state.initializeQuestions()
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_close),
                    contentDescription = stringResource(id = designSystemR.string.navigation_close_icon_description),
                    modifier = Modifier.noRippleClickable {
                        onCloseClick()
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(id = when (quizType) {
                        QuizType.NONE -> R.string.quiz_type
                        QuizType.MULTIPLE_CHOICE -> R.string.multiple_choice_question
                        QuizType.PHOTO_ANSWER -> R.string.photo_answer
                    }),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            state.isCheckingUnfilled.value = true
                            if (state.isUnfilledFieldExists()) {
                                onUnfilledQuestionsExists()
                            } else {
                                onSaveQuestions(
                                    state.multipleChoiceQuestions,
                                    state.photoAnswerQuestions,
                                )
                            }
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(
                            vertical = 8.dp,
                            horizontal = 24.dp,
                        )
                    )
                }
            }
        }

        when (state.quizType) {
            QuizType.MULTIPLE_CHOICE -> {
                itemsIndexed(
                    items = state.multipleChoiceQuestions,
                    key = { _, question -> question.id },
                ) { index, question ->
                    MultipleChoiceQuestionItem(
                        number = index + 1,
                        question = question.question.value,
                        isCheckingUnfilled = state.isCheckingUnfilled.value,
                        options = question.options,
                        onQuestionChange = {
                            state.onQuestionChange(index, it)
                        },
                        onOptionChange = { option, text ->
                            state.onOptionChange(index, option, text)
                        },
                        onDeleteClick = {
                            state.onDeleteQuestion(index)
                        },
                        onUnfilledChecked = {
                            state.isCheckingUnfilled.value = false
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .animateItemPlacement()
                    )
                }
            }

            QuizType.PHOTO_ANSWER -> {
                itemsIndexed(
                    items = state.photoAnswerQuestions,
                    key = { _, question -> question.id }
                ) { index, question ->
                    PhotoAnswerQuestionItem(
                        number = index + 1,
                        question = question.question.value,
                        isCheckingUnfilled = state.isCheckingUnfilled.value,
                        onQuestionChange = {
                            state.onQuestionChange(index, it)
                        },
                        onDeleteClick = {
                            state.onDeleteQuestion(index)
                        },
                        onUnfilledChecked = {
                            state.isCheckingUnfilled.value = false
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .animateItemPlacement()
                    )
                }
            }

            QuizType.NONE -> Unit
        }

        item {
            Text(
                text = stringResource(id = R.string.add_question),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .noRippleClickable {
                        state.onAddQuestion()
                    }
            )
        }
    }
}

@Composable
private fun MultipleChoiceQuestionItem(
    number: Int,
    question: String,
    isCheckingUnfilled: Boolean,
    options: Map<MultipleChoiceOption, MutableState<String>>,
    onQuestionChange: (String) -> Unit,
    onOptionChange: (MultipleChoiceOption, String) -> Unit,
    onDeleteClick: () -> Unit,
    onUnfilledChecked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseQuizQuestionItem(
        number = number,
        question = question,
        isCheckingUnfilled = isCheckingUnfilled,
        onQuestionChange = onQuestionChange,
        onDeleteClick = onDeleteClick,
        onUnfilledChecked = onUnfilledChecked,
        modifier = modifier
    ) {
        options.forEach { option ->
            QuizQuestionsFieldCard(
                value = option.value.value,
                placeholderText = "Option ${option.key}",
                isCheckingUnfilledField = isCheckingUnfilled,
                onValueChange = {
                    onOptionChange(option.key, it)
                },
                onUnfilledFieldChecked = onUnfilledChecked,
            ) { value, placeholder, placeholderColor, onValueChange ->
                Row {
                    Text(
                        text = "${option.key}. ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = if (option.value.value.contains('\n')) {
                            Modifier
                        } else Modifier.align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    TransparentTextField(
                        value = value,
                        placeholderText = placeholder,
                        placeholderTextColor = placeholderColor,
                        onValueChange = onValueChange,
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
private fun PhotoAnswerQuestionItem(
    number: Int,
    question: String,
    isCheckingUnfilled: Boolean,
    onQuestionChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onUnfilledChecked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseQuizQuestionItem(
        number = number,
        question = question,
        isCheckingUnfilled = isCheckingUnfilled,
        onQuestionChange = onQuestionChange,
        onDeleteClick = onDeleteClick,
        onUnfilledChecked = onUnfilledChecked,
        modifier = modifier,
    )
}

@Composable
private fun BaseQuizQuestionItem(
    number: Int,
    question: String,
    isCheckingUnfilled: Boolean,
    onQuestionChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onUnfilledChecked: () -> Unit,
    modifier: Modifier = Modifier,
    optionsContent: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = CircleShape,
                    )
                    .background(MaterialTheme.colorScheme.background)
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

            Spacer(modifier = Modifier.height(16.dp))

            if (number > 1) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_delete),
                    contentDescription = stringResource(id = R.string.delete_question),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally)
                        .noRippleClickable {
                            onDeleteClick()
                        },
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            QuizQuestionsFieldCard(
                value = question,
                placeholderText = stringResource(id = R.string.question_ellipsis),
                isCheckingUnfilledField = isCheckingUnfilled,
                onValueChange = onQuestionChange,
                onUnfilledFieldChecked = onUnfilledChecked,
            ) { value, placeholder, placeholderColor, onValueChange ->
                TransparentTextField(
                    value = value,
                    placeholderText = placeholder,
                    placeholderTextColor = placeholderColor,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (optionsContent != {}) {
                Spacer(modifier = Modifier.height(10.dp))
            }

            optionsContent()
        }
    }
}

@Composable
private fun QuizQuestionsFieldCard(
    value: String,
    placeholderText: String,
    isCheckingUnfilledField: Boolean,
    onValueChange: (String) -> Unit,
    onUnfilledFieldChecked: () -> Unit,
    modifier: Modifier = Modifier,
    fieldContent: @Composable (
        value: String,
        placeholder: String,
        placeholderColor: Color,
        onValueChange: (String) -> Unit
    ) -> Unit,
) {
    val white = MaterialTheme.colorScheme.background
    val red = MaterialTheme.colorScheme.primary
    val gray = MaterialTheme.colorScheme.onSurface

    val backgroundColor = remember { Animatable(white) }
    val placeholderColor = remember { Animatable(gray) }

    LaunchedEffect(isCheckingUnfilledField) {
        if (isCheckingUnfilledField && value.isEmpty()) {
            launch {
                launch {
                    backgroundColor.animateTo(
                        targetValue = red,
                        animationSpec = tween(500),
                    )
                    backgroundColor.animateTo(
                        targetValue = white,
                        animationSpec = tween(500),
                    )
                }
                launch {
                    placeholderColor.animateTo(
                        targetValue = white,
                        animationSpec = tween(500),
                    )
                    placeholderColor.animateTo(
                        targetValue = gray,
                        animationSpec = tween(500),
                    )
                }
            }.join()

            launch {
                onUnfilledFieldChecked()
            }
        }
    }

    BaseCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor.value)
                .padding(10.dp)
                .animateContentSize()
        ) {
            fieldContent(value, placeholderText, placeholderColor.value, onValueChange)
        }
    }
}

data class MultipleChoiceQuestion(
    val id: MutableIntState = mutableIntStateOf(QuestionIdGenerator.getId()),
    val question: MutableState<String> = mutableStateOf(""),
    val options: Map<MultipleChoiceOption, MutableState<String>> = mapOf(
        MultipleChoiceOption.A to mutableStateOf(""),
        MultipleChoiceOption.B to mutableStateOf(""),
        MultipleChoiceOption.C to mutableStateOf(""),
        MultipleChoiceOption.D to mutableStateOf(""),
        MultipleChoiceOption.E to mutableStateOf(""),
    ),
)

enum class MultipleChoiceOption {
    A, B, C, D, E
}

data class PhotoAnswerQuestion(
    val id: MutableIntState = mutableIntStateOf(QuestionIdGenerator.getId()),
    val question: MutableState<String> = mutableStateOf(""),
)

object QuestionIdGenerator {

    private var id = 0

    fun getId() = id++
}