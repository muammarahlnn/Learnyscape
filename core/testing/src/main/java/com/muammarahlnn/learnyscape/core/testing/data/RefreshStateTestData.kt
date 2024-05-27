package com.muammarahlnn.learnyscape.core.testing.data

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TestRefreshState, 27/05/2024 17.00
 */
@OptIn(ExperimentalMaterialApi::class)
val refreshStateTestData: RefreshState
    @Composable get() = RefreshState(
        refreshing = false,
        pullRefreshState = rememberPullRefreshState(
            refreshing = false,
            onRefresh = {},
        )
    )