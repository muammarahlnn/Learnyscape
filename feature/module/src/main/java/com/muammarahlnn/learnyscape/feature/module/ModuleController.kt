package com.muammarahlnn.learnyscape.feature.module

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ModuleController, 18/02/2024 01.10
 */
class ModuleController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToResourceDetails: (String, Int) -> Unit,
    val navigateToResourceCreate: (String, Int) -> Unit,
) : NavigationProvider<ModuleNavigation> by navigation(scope)

sealed interface ModuleNavigation {

    data object NavigateBack : ModuleNavigation

    data class NavigateToResourceDetails(
        val resourceId: String,
        val resourceTypeOrdinal: Int,
    ) : ModuleNavigation

    data class NavigateToResourceCreate(
        val classId: String,
        val resourceTypeOrdinal: Int,
    ) : ModuleNavigation
}