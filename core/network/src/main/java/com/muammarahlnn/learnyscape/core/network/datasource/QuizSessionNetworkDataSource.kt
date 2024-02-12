package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.QuizMultipleChoiceProblemResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionNetworkDataSource, 09/02/2024 18.18
 */
interface QuizSessionNetworkDataSource {

    fun getQuizMultipleChoiceProblems(quizId: String): Flow<List<QuizMultipleChoiceProblemResponse>>

    fun putMultipleChoiceAnswers(
        quizId: String,
        answers: List<String>,
    ): Flow<String>
}