package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ContractProvider, 16/02/2024 21.37
 */
interface ContractProvider<STATE, EVENT, EFFECT> {

    val state: StateFlow<STATE>

    val effect: Flow<EFFECT>

    fun event(event: EVENT)

    fun updateState(block: (STATE) -> STATE)

    fun updateState(state: STATE)

    fun CoroutineScope.emitEffect(effect: EFFECT)
}