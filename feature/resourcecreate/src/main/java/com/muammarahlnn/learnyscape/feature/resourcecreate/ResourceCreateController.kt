package com.muammarahlnn.learnyscape.feature.resourcecreate

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateController, 18/02/2024 13.20
 */
class ResourceCreateController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToCamera: () -> Unit,
) : NavigationProvider<ResourceCreateNavigation> by navigation(scope)

sealed interface ResourceCreateNavigation {

    data object NavigateBack : ResourceCreateNavigation

    data object NavigateToCamera : ResourceCreateNavigation
}