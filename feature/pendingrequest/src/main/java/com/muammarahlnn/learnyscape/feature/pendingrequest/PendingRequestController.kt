package com.muammarahlnn.learnyscape.feature.pendingrequest

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestController, 29/02/2024 22.56
 */
class PendingRequestController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
) : NavigationProvider<PendingRequestNavigation> by navigation(scope)

sealed interface PendingRequestNavigation {

    data object NavigateBack : PendingRequestNavigation
}