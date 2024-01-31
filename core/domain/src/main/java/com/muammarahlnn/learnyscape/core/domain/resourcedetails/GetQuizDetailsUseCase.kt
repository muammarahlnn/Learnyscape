package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.QuizDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetQuizDetailsUseCase, 31/01/2024 01.56
 */
fun interface GetQuizDetailsUseCase {

    operator fun invoke(quizId: String): Flow<QuizDetailsModel>
}