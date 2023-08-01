package com.muammarahlnn.learnyscape.feature.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.defaultTopAppBarColors


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ScheduleScreen, 20/07/2023 22.04 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScheduleTopAppBar()
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.schedule),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = R.string.schedule,
        scrollBehavior = scrollBehavior,
        colors = defaultTopAppBarColors(),
        modifier = modifier,
    )
}