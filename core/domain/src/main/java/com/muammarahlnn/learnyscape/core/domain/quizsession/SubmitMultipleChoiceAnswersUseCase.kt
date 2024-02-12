package com.muammarahlnn.learnyscape.core.domain.quizsession

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmitMultipleChoiceAnswers, 10/02/2024 21.18
 */
fun interface SubmitMultipleChoiceAnswersUseCase {

    operator fun invoke(
        quizId: String,
        answers: List<String>,
    ): Flow<String>
}