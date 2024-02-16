package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File RefreshDelegate, 16/02/2024 20.10
 */
class RefreshDelegate : RefreshProvider {

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing.asStateFlow()
}

fun refresh(): RefreshProvider = RefreshDelegate()