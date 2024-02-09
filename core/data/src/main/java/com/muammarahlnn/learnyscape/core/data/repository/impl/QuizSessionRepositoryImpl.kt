package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toMultipleChoiceQuestionModels
import com.muammarahlnn.learnyscape.core.data.repository.QuizSessionRepository
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.network.datasource.QuizSessionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionRepositoryImpl, 09/02/2024 18.22
 */
class QuizSessionRepositoryImpl @Inject constructor(
    private val quizSessionNetworkDataSource: QuizSessionNetworkDataSource,
) : QuizSessionRepository {

    override fun getQuizMultipleChoiceQuestions(quizId: String): Flow<List<MultipleChoiceQuestionModel>> =
        quizSessionNetworkDataSource.getQuizMultipleChoiceProblems(quizId).map { quizMultipleChoiceProblemResponses ->
            quizMultipleChoiceProblemResponses.toMultipleChoiceQuestionModels()
        }
}