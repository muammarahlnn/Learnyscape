package com.muammarahlnn.learnyscape.feature.profile

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ProfileController, 17/02/2024 21.45
 */
class ProfileController(
    scope: CoroutineScope,
    val navigateToCamera: () -> Unit,
    val navigateToChangePassword: () -> Unit,
    val navigateToLogin: () -> Unit,
) : NavigationProvider<ProfileNavigation> by navigation(scope)

sealed interface ProfileNavigation {

    data object NavigateToCamera : ProfileNavigation

    data object NavigateToChangePassword : ProfileNavigation

    data object NavigateToLogin : ProfileNavigation
}