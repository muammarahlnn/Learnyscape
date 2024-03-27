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
    val navigateToResourceDetails: (String, Int) -> Unit,
) : NavigationProvider<NotificationsNavigation> by navigation(scope)

sealed interface NotificationsNavigation {

    data object NavigateBack : NotificationsNavigation

    data class NavigateToResourceDetails(
        val resourceId: String,
        val resourceTypeOrdinal: Int,
    ) : NotificationsNavigation
}