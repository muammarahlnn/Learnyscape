package com.muammarahlnn.learnyscape.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizScreen, 03/08/2023 15.47 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun QuizRoute(
    onQuizClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    QuizScreen(
        onQuizClick = onQuizClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreen(
    onQuizClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier.fillMaxSize()) {
        ClassTopAppBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick
        )
        QuizContent(
            onQuizClick = onQuizClick,
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizContent(
    onQuizClick: (Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        repeat(20) {
            item {
                ClassResourceCard(
                    classResourceType = ClassResourceType.MODULE,
                    title = "Quiz Local Data Persistent dan Database",
                    timeLabel = "Start at 21 May 2023, 21:21",
                    onItemClick = onQuizClick,
                )
            }
        }
    }
}