package com.muammarahlnn.learnyscape.feature.aclass

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
 * @file ClassScreen, 04/08/2023 00.07 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClassScreen(
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClassScreen(
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
                text = stringResource(id = R.string.announcement),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}