package com.muammarahlnn.learnyscape.feature.assignment

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
 * @file AssignmentScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun AssignmentRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssignmentScreen(
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignmentScreen(
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
                text = stringResource(id = R.string.assignment),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}