package com.muammarahlnn.learnyscape.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp


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

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HomeToolbar()
        HomeContent()
    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.home),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun HomeToolbar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LearnyscapeText()
        NotificationsIcon()
    }
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
private fun NotificationsIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_notification),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground
    )
}