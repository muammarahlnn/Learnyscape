package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.base.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import retrofit2.http.Header
import retrofit2.http.POST


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UsersApi, 30/09/2023 19.46 by Muammar Ahlan Abimanyu
 */
interface UsersApi {

    @POST(LOGIN_END_POINT)
    suspend fun postLogin(
        @Header(AUTHORIZATION) basicAuth: String
    ): BaseResponse<LoginResponse>

    companion object {

        private const val AUTHORIZATION = "Authorization"

        private const val LOGIN_END_POINT = "users/login"
    }
}