package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.QuizzesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.datasource.SubmissionDetailsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.StudentQuizAnswerResponse
import com.muammarahlnn.learnyscape.core.network.model.response.StudentTaskSubmissionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsNetworkDataSourceImpl, 13/02/2024 19.38
 */
@Singleton
class SubmissionDetailsNetworkDataSourceImpl @Inject constructor(
    private val tasksApi: TasksApi,
    private val quizzesApi: QuizzesApi,
) : SubmissionDetailsNetworkDataSource  {

    override fun getTaskSubmissionDetails(submissionId: String): Flow<StudentTaskSubmissionResponse> =
        flow {
            emit(tasksApi.getTaskSubmissionDetails(submissionId).data)
        }

    override fun getStudentQuizAnswers(
        quizId: String,
        studentId: String
    ): Flow<List<StudentQuizAnswerResponse>> = flow {
        emit(quizzesApi.getStudentQuizAnswers(quizId, studentId).data)
    }
}