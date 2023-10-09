package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.LoginApi
import com.muammarahlnn.learnyscape.core.network.datasource.LoginNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import com.muammarahlnn.learnyscape.core.network.model.response.UserResponse
import com.muammarahlnn.learnyscape.core.network.model.response.base.NetworkResult
import com.muammarahlnn.learnyscape.core.network.model.response.base.handleApi
import kotlinx.coroutines.flow.Flow
import okhttp3.Credentials
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginNetworkDataSourceImpl, 02/10/2023 11.52 by Muammar Ahlan Abimanyu
 */

@Singleton
class LoginNetworkDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi,
) : LoginNetworkDataSource {

    override fun postLogin(
        username: String,
        password: String,
    ): Flow<NetworkResult<LoginResponse>> {
        val basicAuth = Credentials.basic(username, password)
        return handleApi {
            loginApi.postLogin(basicAuth)
        }
    }

    override fun getCredential(token: String): Flow<NetworkResult<UserResponse>> {
        val bearerToken = "Bearer $token"
        return handleApi {
            loginApi.getCredential(bearerToken)
        }
    }
}