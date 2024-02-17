package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NavigationProvider, 17/02/2024 15.43
 */
interface NavigationProvider<Navigation> {

    val navigation: Flow<Navigation>

    fun navigate(navigation: Navigation)
}