package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.request.TurnInTaskSubmissionRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LecturerTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.StudentTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskOverviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TasksApi, 16/01/2024 11.39
 */
interface TasksApi {

    @Multipart
    @POST("tasks")
    suspend fun postTask(
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.CLASS_ID_PART) classId: RequestBody,
        @Part(ResourceClassPartKey.NAME_PART) title: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody?,
        @Part(ResourceClassPartKey.DUE_DATE_PART) dueDate: RequestBody,
    ): BaseResponse<String>

    @GET("tasks/classes/{classId}")
    suspend fun getTasks(
        @Path("classId") classId: String
    ): BaseResponse<List<TaskOverviewResponse>>

    @GET("tasks/{taskId}")
    suspend fun getTaskDetails(
        @Path("taskId") taskId: String,
    ): BaseResponse<TaskDetailsResponse>

    @DELETE("tasks/{taskId}")
    suspend fun deleteTask(
        @Path("taskId") taskId: String,
    ): BaseResponse<String>

    @GET("tasks/{taskId}/submissions")
    suspend fun lecturerGetTaskSubmissions(
        @Path("taskId") taskId: String,
    ): BaseResponse<List<LecturerTaskSubmissionResponse>>

    @GET("tasks/{taskId}/submissions")
    suspend fun studentGetTaskSubmission(
        @Path("taskId") taskId: String,
    ): BaseResponse<StudentTaskSubmissionResponse>

    @Multipart
    @POST("tasks/submissions")
    suspend fun uploadTaskSubmission(
        @Part("taskId") taskId: RequestBody,
        @Part files: List<MultipartBody.Part>,
    ): BaseResponse<String>

    @Multipart
    @PUT("tasks/submissions/{submissionId}")
    suspend fun updateTaskSubmission(
        @Path("submissionId") submissionId: String,
        @Part files: List<MultipartBody.Part>,
    ): BaseResponse<String>

    @PUT("tasks/submissions/{submissionId}/turn-in")
    suspend fun turnInTaskSubmission(
        @Path("submissionId") submissionId: String,
        @Body turnInTaskSubmissionRequest: TurnInTaskSubmissionRequest,
    ): BaseResponse<String>
}