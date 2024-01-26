package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.request.RequestJoinClassRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ClassMembersResponse
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
import retrofit2.http.Url


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginApi, 08/10/2023 15.30 by Muammar Ahlan Abimanyu
 */
interface UsersApi {

    @POST("users/login")
    suspend fun postLogin(
        @Header("Authorization") basicAuth: String
    ): BaseResponse<LoginResponse>

    @GET("users")
    suspend fun getCredential(
        @Header("Authorization") bearerToken: String,
    ): BaseResponse<UserResponse>

    @GET("users/classes")
    suspend fun getEnrolledClasses(): BaseResponse<List<EnrolledClassInfoResponse>>

    @PUT("users/classes")
    suspend fun putRequestJoinClass(
        @Body requestJoinClassRequest: RequestJoinClassRequest
    ): BaseResponse<String>

    @Multipart
    @POST("users/pic")
    suspend fun postProfilePic(
        @Part pic: MultipartBody.Part,
    ): BaseResponse<String>

    @GET("users/pic")
    suspend fun getProfilePic(): Response<ResponseBody>

    @GET("users/schedules")
    suspend fun getSchedules(): BaseResponse<List<ScheduleResponse>>

    @GET("users/classes/{classId}/waiting-lists")
    suspend fun getWaitingList(
        @Path("classId") classId: String,
        @Query("status") waitingListStatus: String = WaitingListStatusResponse.PENDING.name,
    ): BaseResponse<List<WaitingListResponse>>

    @GET("users/classes/{classId}/students")
    suspend fun getClassMembers(
        @Path("classId") classId: String,
    ): BaseResponse<ClassMembersResponse>

    @GET
    suspend fun getProfilePicByUrl(
        @Url profilePicUrl: String,
    ): Response<ResponseBody>
}