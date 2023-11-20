package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.request.RequestJoinClassRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.EnrolledClassInfoResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import com.muammarahlnn.learnyscape.core.network.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


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

    companion object {

        private const val AUTHORIZATION = "Authorization"

        private const val USERS_END_POINT = "users"

        private const val USERS_LOGIN_END_POINT = "users/login"

        private const val USERS_CLASSES_END_POINT = "users/classes"
    }
}