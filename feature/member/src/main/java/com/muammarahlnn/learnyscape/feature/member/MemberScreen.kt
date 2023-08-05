package com.muammarahlnn.learnyscape.feature.member

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MemberScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */
@Composable
internal fun MemberScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.member),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}