package com.muammarahlnn.learnyscape.core.domain.base


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file BaseSuspendUseCase, 07/10/2023 03.08 by Muammar Ahlan Abimanyu
 */
interface BaseSuspendUseCase<Params> {

    suspend fun execute(params: Params)
}