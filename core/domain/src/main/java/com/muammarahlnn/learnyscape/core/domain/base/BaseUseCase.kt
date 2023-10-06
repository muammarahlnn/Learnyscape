package com.muammarahlnn.learnyscape.core.domain.base

import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file BaseUseCase, 02/10/2023 16.31 by Muammar Ahlan Abimanyu
 */
interface BaseUseCase<Params, T> {

    fun execute(params: Params): Flow<T>
}