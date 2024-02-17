package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NavigationDelegate, 17/02/2024 15.52
 */
private class NavigationDelegate<Navigation>(
    private val coroutineScope: CoroutineScope,
) : NavigationProvider<Navigation> {

    private val _navigation by lazy {
        Channel<Navigation>()
    }
    override val navigation: Flow<Navigation> by lazy { _navigation.receiveAsFlow() }

    override fun navigate(navigation: Navigation) {
        coroutineScope.launch { _navigation.send(navigation) }
    }
}

fun <Navigation> navigation(
     coroutineScope: CoroutineScope,
): NavigationProvider<Navigation> = NavigationDelegate(coroutineScope)