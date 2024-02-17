package com.muammarahlnn.learnyscape.feature.search

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SearchController, 17/02/2024 20.05
 */
class SearchController(
    scope: CoroutineScope,
    val navigateToPendingRequestClass: () -> Unit,
) : NavigationProvider<SearchContract.Navigation> by navigation(scope)