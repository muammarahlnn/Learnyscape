package com.muammarahlnn.learnyscape.feature.announcement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AnnouncementScreen, 03/08/2023 15.44 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun AnnouncementScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Test",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}