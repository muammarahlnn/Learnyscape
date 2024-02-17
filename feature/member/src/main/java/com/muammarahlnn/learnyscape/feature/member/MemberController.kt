package com.muammarahlnn.learnyscape.feature.member

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MemberController, 18/02/2024 01.43
 */
class MemberController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
) : NavigationProvider<MemberNavigation> by navigation(scope)

sealed interface MemberNavigation {

    data object NavigateBack : MemberNavigation
}