package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DeleteQuizUseCase, 27/03/2024 22.22
 */
fun interface DeleteQuizUseCase {

    operator fun invoke(quizId: String): Flow<String>
}