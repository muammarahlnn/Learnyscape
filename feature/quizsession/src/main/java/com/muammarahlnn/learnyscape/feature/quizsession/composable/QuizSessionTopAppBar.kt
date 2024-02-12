package com.muammarahlnn.learnyscape.feature.quizsession.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.feature.quizsession.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionTopAppBar, 29/08/2023 11.41 by Muammar Ahlan Abimanyu
 */

@Composable
fun QuizSessionTopAppBar(
    quizName: String,
    quizDuration: Int,
    topAppBarOffsetHeightPx: Float,
    isSubmittingAnswers: Boolean,
    onGloballyPositioned: (Float) -> Unit,
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier,
    state: QuizSessionTopAppBarState = rememberQuizSessionTopAppBarState(
        quizDuration = quizDuration,
    ),
) {
    LaunchedEffect(state.currentRemainingTime, isSubmittingAnswers) {
        if (!isSubmittingAnswers) {
            delay(1000L)
            state.updateCurrentRemainingTime()
        }
    }

    val currentTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(state.isTimeout) {
        if (state.isTimeout) {
            currentTimeout()
        }
    }

    val localDensity = LocalDensity.current
    val topAppBarPadding = 16.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .offset {
                IntOffset(x = 0, y = topAppBarOffsetHeightPx.roundToInt())
            }
            .background(MaterialTheme.colorScheme.primary)
            .padding(topAppBarPadding)
            .onGloballyPositioned { layoutCoordinates ->
                with(localDensity) {
                    // topAppBarHeight = content height + top padding + bottom padding
                    onGloballyPositioned(
                        layoutCoordinates.size.height.toFloat()
                                + topAppBarPadding.toPx()
                                + topAppBarPadding.toPx()
                    )
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (state.isQuizNameSingleLine) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_quiz),
                    contentDescription = stringResource(
                        id = R.string.quiz
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(
                text = stringResource(
                    R.string.quiz_name,
                    quizName,
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    state.setIsQuizNameSingleLine(textLayoutResult.lineCount <= 1)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(
                R.string.remaining_time,
                state.textTime
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun rememberQuizSessionTopAppBarState(quizDuration: Int): QuizSessionTopAppBarState =
    rememberSaveable(quizDuration, saver = QuizSessionTopAppBarState.saver) {
        QuizSessionTopAppBarState(quizDuration = quizDuration)
    }

class QuizSessionTopAppBarState(quizDuration: Int) {

    var currentRemainingTime by mutableIntStateOf(quizDuration * 60) // convert to seconds
        private set

    var textTime by mutableStateOf(formatToMinutesAndSeconds())
        private set

    var isQuizNameSingleLine by mutableStateOf(false)
        private set

    var isTimeout by mutableStateOf(false)
        private set

    fun updateCurrentRemainingTime() {
        if (currentRemainingTime <= 0) {
            isTimeout = true
            return
        }
        currentRemainingTime--
        textTime = formatToMinutesAndSeconds()
    }

    fun setIsQuizNameSingleLine(isQuizNameSingleLine: Boolean) {
        this.isQuizNameSingleLine = isQuizNameSingleLine
    }

    private fun formatToMinutesAndSeconds(): String {
        val minutes = currentRemainingTime / 60
        val seconds = currentRemainingTime % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    companion object {
        val saver: Saver<QuizSessionTopAppBarState, Any> = listSaver(
            save = {
                listOf(it.currentRemainingTime)
            },
            restore = {
                QuizSessionTopAppBarState(it[0])
            }
        )
    }
}