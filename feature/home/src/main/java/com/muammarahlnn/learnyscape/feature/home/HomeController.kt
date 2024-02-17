package com.muammarahlnn.learnyscape.feature.home

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation

/**
 * @Author Muammar Ahlan Abimanyu
 * @File HomeController, 17/02/2024 19.08
 */
data class HomeController(
    val navigateToClass: (String) -> Unit,
    val navigateToNotifications: () -> Unit,
) : NavigationProvider<HomeContract.Navigation> by navigation()