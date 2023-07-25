package com.muammarahlnn.learnyscape.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.core.designsystem.component.homeTopAppBarColors


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeScreen, 20/07/2023 19.56 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
) {
    HomeScreen(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

        HomeTopAppBar(
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
                ClassItem(
                    modifier = Modifier.padding(
                        vertical = 4.dp,
                        horizontal = 16.dp
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
        onActionClick = {
            // TODO: will implement later
        }
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

@Composable
private fun ClassItem(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_group),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                // this is a hardcoded text just for dummy purpose
                Text(
                    text = "Pemrograman Mobile B",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "Lorem ipsum dolor sit amet lorem ipsum dolor sit amet",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}