package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File IsQuizTakenUseCase, 13/02/2024 14.18
 */
fun interface IsQuizTakenUseCase {

    operator fun invoke(quizId: String): Flow<Boolean>
}