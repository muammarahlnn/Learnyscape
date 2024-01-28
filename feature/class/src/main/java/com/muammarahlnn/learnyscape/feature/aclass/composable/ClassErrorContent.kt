package com.muammarahlnn.learnyscape.feature.aclass.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassErrorContent, 27/01/2024 23.32
 */
@Composable
internal fun ClassErrorContent(
    errorMessage: String,
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        ClassNavigationAndActionTopIcons(
            onBackClick = onBackClick,
            onJoinRequestsClick = onJoinRequestsClick,
        )

        ErrorScreen(
            text = errorMessage,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = iconBoxSize)
        )
    }
}