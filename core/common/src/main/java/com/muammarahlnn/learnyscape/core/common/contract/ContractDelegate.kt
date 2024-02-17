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
private class ContractDelegate<State, Event, Effect>(
    initialState: State,
) : ContractProvider<State, Event, Effect> {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    private val _effect by lazy { Channel<Effect>() }
    override val effect: Flow<Effect> by lazy { _effect.receiveAsFlow() }

    override fun event(event: Event) {}

    override fun updateState(state: State) {
        _state.update { state }
    }

    override fun updateState(block: (State) -> State) {
        _state.update(block)
    }

    override fun CoroutineScope.emitEffect(effect: Effect) {
        this.launch { _effect.send(effect) }
    }
}

fun <State, Event, Effect> contract(initialState: State): ContractProvider<State, Event, Effect> =
    ContractDelegate(initialState)