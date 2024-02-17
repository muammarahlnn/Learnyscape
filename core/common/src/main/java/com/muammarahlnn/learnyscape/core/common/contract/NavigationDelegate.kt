package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NavigationDelegate, 17/02/2024 15.52
 */
private class NavigationDelegate<Navigation> : NavigationProvider<Navigation> {

    private val _navigation by lazy {
        Channel<Navigation>(
            capacity = Channel.UNLIMITED,
            onBufferOverflow = BufferOverflow.DROP_LATEST,
        )
    }
    override val navigation: Flow<Navigation> by lazy { _navigation.receiveAsFlow() }

    override fun navigate(navigation: Navigation) {
        _navigation.trySend(navigation)
    }
}

fun <Navigation> navigation(): NavigationProvider<Navigation> = NavigationDelegate()