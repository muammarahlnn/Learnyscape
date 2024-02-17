package com.muammarahlnn.learnyscape.feature.schedule

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleController, 17/02/2024 21.24
 */
class ScheduleController(
    scope: CoroutineScope,
    val navigateToClass: (String) -> Unit,
) : NavigationProvider<ScheduleNavigation> by navigation(scope)

sealed interface ScheduleNavigation {

    data class NavigateToClass(val classId: String) : ScheduleNavigation
}