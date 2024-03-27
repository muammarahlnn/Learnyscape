package com.muammarahlnn.learnyscape.feature.changepassword

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordController, 27/03/2024 15.56
 */
class ChangePasswordController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
) : NavigationProvider<ChangePasswordNavigation> by navigation(scope)

sealed interface ChangePasswordNavigation {

    data object NavigateBack : ChangePasswordNavigation
}