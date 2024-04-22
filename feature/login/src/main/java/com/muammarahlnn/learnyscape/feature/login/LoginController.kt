package com.muammarahlnn.learnyscape.feature.login

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LoginController, 22/04/2024 22.55
 */
class LoginController(
    val navigateToHomeNavigator: () -> Unit,
    scope: CoroutineScope,
) : NavigationProvider<LoginNavigation> by navigation(scope)

sealed interface LoginNavigation {

    data object NavigateToHomeNavigator : LoginNavigation
}