package com.muammarahlnn.learnyscape.feature.assignment

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentController, 18/02/2024 01.24
 */
class AssignmentController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToResourceDetails: (String, Int) -> Unit,
    val navigateToResourceCreate: (String, Int) -> Unit,
) : NavigationProvider<AssignmentNavigation> by navigation(scope)

sealed interface AssignmentNavigation {

    data object NavigateBack : AssignmentNavigation

    data class NavigateToResourceDetails(
        val resourceId: String,
        val resourceTypeOrdinal: Int,
    ) : AssignmentNavigation

    data class NavigateToResourceCreate(
        val classId: String,
        val resourceTypeOrdinal: Int,
    ) : AssignmentNavigation
}