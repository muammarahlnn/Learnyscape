package com.muammarahlnn.learnyscape.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.homeTopAppBarColors
import com.muammarahlnn.learnyscape.core.ui.ClassCard


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeScreen, 20/07/2023 19.56 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun HomeRoute(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeScreen(
        onNotificationsClick = onNotificationsClick,
        onClassClick = onClassClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    onNotificationsClick: () -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

        HomeTopAppBar(
            onNotificationsClick = onNotificationsClick,
            scrollBehavior = scrollBehavior,
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            // for now this items just for dummy purpose
            items(
                items = (1..10).toList(),
                key = { it },
            ) {
                ClassCard(
                    onItemClick = onClassClick,
                    modifier = Modifier.padding(
                        vertical = 4.dp,
                        horizontal = 16.dp,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = {
            LearnyscapeText()
        },
        actionIconRes = R.drawable.ic_notification,
        actionIconContentDescription = stringResource(id = R.string.top_app_bar_navigation_icon_description),
        colors = homeTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        onActionClick = onNotificationsClick
    )
}

@Composable
private fun LearnyscapeText() {
    val learnyscapeString = stringResource(id = R.string.learnyscape)
    val scapeStartIndex = 6
    Text(
        text = AnnotatedString(
            text = learnyscapeString,
            spanStyles = listOf(
                AnnotatedString.Range(
                    SpanStyle(color = MaterialTheme.colorScheme.primary),
                    start = scapeStartIndex,
                    end = learnyscapeString.length
                ),
            ),
        ),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
}