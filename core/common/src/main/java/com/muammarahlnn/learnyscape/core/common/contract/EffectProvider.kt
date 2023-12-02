package com.muammarahlnn.learnyscape.core.common.contract

import kotlinx.coroutines.flow.SharedFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EffectProvider, 02/12/2023 20.07
 */
interface EffectProvider<EFFECT> {

    val effect: SharedFlow<EFFECT>
}