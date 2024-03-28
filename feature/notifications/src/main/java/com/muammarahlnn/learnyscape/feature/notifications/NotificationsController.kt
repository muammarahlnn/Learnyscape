package com.muammarahlnn.learnyscape.feature.notifications

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsController, 27/03/2024 01.43
 */
class NotificationsController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToResourceDetails: (String, String, Int) -> Unit,
) : NavigationProvider<NotificationsNavigation> by navigation(scope)

sealed interface NotificationsNavigation {

    data object NavigateBack : NotificationsNavigation

    data class NavigateToResourceDetails(
        val classId: String = EMPTY_CLASS_ID,
        val resourceId: String,
        val resourceTypeOrdinal: Int,
    ) : NotificationsNavigation
}

// notifications screen is for student only, so we just need
// to pass empty class id because class id is only needed for lecturer
// to edit the resource
private const val EMPTY_CLASS_ID = ""