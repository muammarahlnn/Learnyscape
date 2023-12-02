package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.flow.StateFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File BaseContract, 02/12/2023 20.06
 */
interface BaseContract<STATE, EVENT> {

    val state: StateFlow<STATE>

    fun event(event: EVENT)
}