package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.request.RequestJoinClassRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.EnrolledClassInfoResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ScheduleResponse
import com.muammarahlnn.learnyscape.core.network.model.response.UserResponse
import com.muammarahlnn.learnyscape.core.network.model.response.WaitingListResponse
import com.muammarahlnn.learnyscape.core.network.model.response.WaitingListStatusResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginApi, 08/10/2023 15.30 by Muammar Ahlan Abimanyu
 */
interface UsersApi {

    @POST(USERS_LOGIN_END_POINT)
    suspend fun postLogin(
        @Header(AUTHORIZATION) basicAuth: String
    ): BaseResponse<LoginResponse>

    @GET(USERS_END_POINT)
    suspend fun getCredential(
        @Header(AUTHORIZATION) bearerToken: String,
    ): BaseResponse<UserResponse>

    @GET(USERS_CLASSES_END_POINT)
    suspend fun getEnrolledClasses(): BaseResponse<List<EnrolledClassInfoResponse>>

    @PUT(USERS_CLASSES_END_POINT)
    suspend fun putRequestJoinClass(
        @Body requestJoinClassRequest: RequestJoinClassRequest
    ): BaseResponse<String>

    @Multipart
    @POST(USERS_PIC_END_POINT)
    suspend fun postProfilePic(
        @Part pic: MultipartBody.Part,
    ): BaseResponse<String>

    @GET(USERS_PIC_END_POINT)
    suspend fun getProfilePic(): Response<ResponseBody>

    @GET(USERS_SCHEDULES_END_POINT)
    suspend fun getSchedules(): BaseResponse<List<ScheduleResponse>>

    @GET(WAITING_LIST_END_POINT)
    suspend fun getWaitingList(
        @Path(CLASS_ID_PATH) classId: String,
        @Query(STATUS_QUERY) waitingListStatus: String = WaitingListStatusResponse.PENDING.name,
    ): BaseResponse<List<WaitingListResponse>>

    companion object {

        private const val AUTHORIZATION = "Authorization"

        private const val CLASS_ID_PATH = "classId"

        private const val STATUS_QUERY = "status"

        private const val USERS_END_POINT = "users"

        private const val USERS_LOGIN_END_POINT = "$USERS_END_POINT/login"

        private const val USERS_CLASSES_END_POINT = "$USERS_END_POINT/classes"

        private const val USERS_PIC_END_POINT = "$USERS_END_POINT/pic"

        private const val USERS_SCHEDULES_END_POINT = "$USERS_END_POINT/schedules"

        private const val WAITING_LIST_END_POINT =
            "$USERS_END_POINT/classes/{$CLASS_ID_PATH}/waiting-lists"
    }
}