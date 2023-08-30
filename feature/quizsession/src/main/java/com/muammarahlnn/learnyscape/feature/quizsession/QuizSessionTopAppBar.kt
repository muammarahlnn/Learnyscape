package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionTopAppBar, 29/08/2023 11.41 by Muammar Ahlan Abimanyu
 */

@Composable
fun QuizSessionTopAppBar(
    quizName: String,
    quizDuration: Int,
    modifier: Modifier = Modifier,
    state: QuizSessionTopAppBarState = rememberQuizSessionTopAppBarState(
        quizDuration = quizDuration,
    ),
) {
    LaunchedEffect(state.currentRemainingTime) {
        delay(1000L)
        state.updateCurrentRemainingTime()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
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

    var currentRemainingTime by mutableIntStateOf(quizDuration)
        private set

    var textTime by mutableStateOf(formatToMinutesAndSeconds())
        private set

    var isQuizNameSingleLine by mutableStateOf(false)
        private set

    fun updateCurrentRemainingTime() {
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