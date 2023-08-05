package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.classTopAppBarColors
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassTopAppBar, 05/08/2023 12.01 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        titleRes = R.string.aclass,
        navigationIconRes = designSystemR.drawable.ic_arrow_back_bold,
        navigationIconContentDescription = stringResource(
            id = designSystemR.string.navigation_back_icon_description,
        ),
        colors = classTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        onNavigationClick = onBackClick,
        modifier = modifier,
    )
}