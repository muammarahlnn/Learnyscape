package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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