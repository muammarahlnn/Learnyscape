package com.muammarahlnn.learnyscape.core.domain.quizsession

import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetQuizMultipleChoiceQuestionsUseCase, 09/02/2024 18.25
 */
fun interface GetQuizMultipleChoiceQuestionsUseCase {

    operator fun invoke(quizId: String): Flow<List<MultipleChoiceQuestionModel>>
}