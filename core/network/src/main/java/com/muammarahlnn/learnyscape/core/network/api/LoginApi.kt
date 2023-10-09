package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import com.muammarahlnn.learnyscape.core.network.model.response.UserResponse
import com.muammarahlnn.learnyscape.core.network.model.response.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginApi, 08/10/2023 15.30 by Muammar Ahlan Abimanyu
 */
interface LoginApi {

    @POST(POST_LOGIN_END_POINT)
    suspend fun postLogin(
        @Header(AUTHORIZATION) basicAuth: String
    ): BaseResponse<LoginResponse>

    @GET(GET_CREDENTIAL_END_POINT)
    suspend fun getCredential(
        @Header(AUTHORIZATION) bearerToken: String,
    ): BaseResponse<UserResponse>

    companion object {

        private const val AUTHORIZATION = "Authorization"

        private const val POST_LOGIN_END_POINT = "users/login"

        private const val GET_CREDENTIAL_END_POINT = "users"
    }
}