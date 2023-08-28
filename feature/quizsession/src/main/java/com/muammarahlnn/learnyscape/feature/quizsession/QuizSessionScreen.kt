package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


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
        modifier = modifier,
    )
}

@Composable
private fun QuizSessionScreen(
    quizName: String,
    quizDuration: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        QuizSessionTopAppBar(
            quizName = quizName,
            quizDuration = quizDuration,
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.quiz),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun QuizSessionTopAppBar(
    quizName: String,
    quizDuration: Int,
    modifier: Modifier = Modifier,
) {
    val minutes = quizDuration / 60
    val seconds = minutes % 60
    val time = LocalTime.of(minutes, seconds)
    val formatter = DateTimeFormatter.ofPattern("mm:ss")
    val formattedTime = time.format(formatter)

    // TODO: Make it stateless
    var isQuizNameSingleLine by remember { mutableStateOf(false) }
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
            if (isQuizNameSingleLine) {
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
                    isQuizNameSingleLine = textLayoutResult.lineCount <= 1
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(
                R.string.remaining_time,
                formattedTime
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}