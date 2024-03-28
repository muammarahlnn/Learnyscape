package com.muammarahlnn.learnyscape.feature.aclass

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassNavigation, 18/02/2024 00.02
 */

class ClassController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToJoinRequests: (String) -> Unit,
    val navigateToResourceCreate: (String, Int) -> Unit,
    val navigateToResourceDetails: (String, String, Int) -> Unit,
) : NavigationProvider<ClassNavigation> by navigation(scope)

sealed interface ClassNavigation {

    data object NavigateBack : ClassNavigation

    data class NavigateToJoinRequests(val classId: String) : ClassNavigation

    data class NavigateToResourceCreate(
        val classId: String,
        val resourceTypeOrdinal: Int,
    ) : ClassNavigation

    data class NavigateToResourceDetails(
        val classId: String,
        val resourceId: String,
        val resourceTypeOrdinal: Int,
    ) : ClassNavigation
}