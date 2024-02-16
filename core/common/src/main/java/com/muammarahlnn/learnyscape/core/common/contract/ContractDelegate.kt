package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ContractDelegate, 16/02/2024 19.35
 */
private class ContractDelegate<STATE, EVENT, EFFECT>(
    initialState: STATE,
) : ContractProvider<STATE, EVENT, EFFECT> {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<STATE> = _state.asStateFlow()

    private val _effect by lazy { Channel<EFFECT>() }
    override val effect: Flow<EFFECT> by lazy { _effect.receiveAsFlow() }

    override fun event(event: EVENT) {}

    override fun updateState(state: STATE) {
        _state.update { state }
    }

    override fun updateState(block: (STATE) -> STATE) {
        _state.update(block)
    }

    override fun CoroutineScope.emitEffect(effect: EFFECT) {
        this.launch { _effect.send(effect) }
    }
}

fun <STATE, EVENT, EFFECT> contract(initialState: STATE): ContractProvider<STATE, EVENT, EFFECT> =
    ContractDelegate(initialState)