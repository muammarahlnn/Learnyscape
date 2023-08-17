package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.defaultTopAppBarColors
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsScreen, 18/08/2023 00.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ResourceDetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceDetailsScreen(
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
private fun ResourceDetailsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ResourceDetailsTopAppBar(onBackClick = onBackClick)
        ResourceDetailsContent()
    }
}

@Composable
private fun ResourceDetailsContent(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Details",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResourceDetailsTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LearnyscapeTopAppBar(
        title = "Details",
        navigationIconRes = designSystemR.drawable.ic_arrow_back_bold,
        navigationIconContentDescription = stringResource(
            id = designSystemR.string.navigation_back_icon_description,
        ),
        colors = defaultTopAppBarColors(),
        onNavigationClick = onBackClick,
        modifier = modifier,
    )
}