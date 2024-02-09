package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionRepository, 09/02/2024 18.20
 */
interface QuizSessionRepository {

    fun getQuizMultipleChoiceQuestions(quizId: String): Flow<List<MultipleChoiceQuestionModel>>
}