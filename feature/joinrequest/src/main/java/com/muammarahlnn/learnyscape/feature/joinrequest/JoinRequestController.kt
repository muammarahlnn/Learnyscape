package com.muammarahlnn.learnyscape.feature.joinrequest

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestController, 18/02/2024 01.53
 */
class JoinRequestController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
) : NavigationProvider<JoinRequestNavigation> by navigation(scope)

sealed interface JoinRequestNavigation {

    data object NavigateBack : JoinRequestNavigation
}