package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskOverviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TasksApi, 16/01/2024 11.39
 */
interface TasksApi {

    @Multipart
    @POST(TASKS_END_POINT)
    suspend fun postTask(
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.CLASS_ID_PART) classId: RequestBody,
        @Part(ResourceClassPartKey.NAME_PART) title: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody?,
        @Part(ResourceClassPartKey.DUE_DATE_PART) dueDate: RequestBody,
    ): BaseResponse<String>

    @GET(GET_TASKS_END_POINT)
    suspend fun getTasks(
        @Path(CLASS_ID_PATH) classId: String
    ): BaseResponse<List<TaskOverviewResponse>>

    companion object {

        private const val TASKS_END_POINT = "tasks"

        private const val CLASS_ID_PATH = "classId"

        private const val GET_TASKS_END_POINT = "$TASKS_END_POINT/{$CLASS_ID_PATH}"
    }
}