package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.QuizzesApi
import com.muammarahlnn.learnyscape.core.network.datasource.QuizSessionNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.QuizMultipleChoiceProblemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionNetworkDataSourceImpl, 09/02/2024 18.19
 */
@Singleton
class QuizSessionNetworkDataSourceImpl @Inject constructor(
    private val quizzesApi: QuizzesApi,
) : QuizSessionNetworkDataSource {

    override fun getQuizMultipleChoiceProblems(quizId: String): Flow<List<QuizMultipleChoiceProblemResponse>> =
        flow {
            emit(quizzesApi.getQuizMultipleChoiceProblems(quizId).data.problems)
        }
}