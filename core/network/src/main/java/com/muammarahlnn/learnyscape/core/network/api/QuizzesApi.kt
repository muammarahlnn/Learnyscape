package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.request.AddQuizQuestionsRequest
import com.muammarahlnn.learnyscape.core.network.model.request.CreateQuizRequest
import com.muammarahlnn.learnyscape.core.network.model.request.SubmitMultipleChoiceAnswersRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.CreateQuizResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizMultipleChoiceProblemsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizSubmissionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("quizzes/classes/{classId}")
    suspend fun getQuizzes(
        @Path("classId") classId: String,
    ): BaseResponse<List<QuizOverviewResponse>>

    @GET("quizzes/{quizId}")
    suspend fun getQuizDetails(
        @Path("quizId") quizId: String,
    ): BaseResponse<QuizDetailsResponse>

    @GET("quizzes/{quizId}/students")
    suspend fun getQuizSubmissions(
        @Path("quizId") quizId: String,
    ): BaseResponse<List<QuizSubmissionResponse>>

    @GET("quizzes/{quizId}/problems")
    suspend fun getQuizMultipleChoiceProblems(
        @Path("quizId") quizId: String,
    ): BaseResponse<QuizMultipleChoiceProblemsResponse>

    @PUT("quizzes/{quizId}/problems/choice")
    suspend fun putMultipleChoiceAnswers(
        @Path("quizId") quizId: String,
        @Body submitMultipleChoiceAnswersRequest: SubmitMultipleChoiceAnswersRequest,
    ): BaseResponse<String>
}