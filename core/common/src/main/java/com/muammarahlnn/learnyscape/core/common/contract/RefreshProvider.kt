package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.flow.StateFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File RefreshProvider, 12/12/2023 18.40
 */
interface RefreshProvider {

    val refreshing: StateFlow<Boolean>
}