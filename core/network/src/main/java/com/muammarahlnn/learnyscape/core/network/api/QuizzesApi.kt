package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.request.AddQuizQuestionsRequest
import com.muammarahlnn.learnyscape.core.network.model.request.CreateQuizRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.CreateQuizResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizzesApi, 17/01/2024 14.32
 */
interface QuizzesApi {

    @POST("quizzes")
    suspend fun postQuiz(
        @Body createQuizRequest: CreateQuizRequest
    ): BaseResponse<CreateQuizResponse>

    @POST("quizzes/{quizId}/problems")
    suspend fun postQuizProblems(
        @Path("quizId") quizId: String,
        @Body addProblemsQuizRequest: AddQuizQuestionsRequest,
    ): BaseResponse<String>
}