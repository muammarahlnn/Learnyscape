package com.muammarahlnn.learnyscape.feature.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizScreen, 03/08/2023 15.47 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    QuizScreen(
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ClassTopAppBar(onBackClick = onBackClick)
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.quiz),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}