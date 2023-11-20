package com.muammarahlnn.learnyscape.core.domain.availableclass

import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file RequestJoinClassUseCase, 20/11/2023 17.09 by Muammar Ahlan Abimanyu
 */
fun interface RequestJoinClassUseCase {

    operator fun invoke(classId: String): Flow<String>
}