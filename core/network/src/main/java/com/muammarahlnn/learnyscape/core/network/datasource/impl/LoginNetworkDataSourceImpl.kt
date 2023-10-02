package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.LoginNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Credentials
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginNetworkDataSourceImpl, 02/10/2023 11.52 by Muammar Ahlan Abimanyu
 */

@Singleton
class LoginNetworkDataSourceImpl @Inject constructor(
    private val usersApi: UsersApi,
) : LoginNetworkDataSource {

    override fun postLogin(
        username: String,
        password: String,
    ): Flow<LoginResponse> {
        val basicAuth = Credentials.basic(username, password)
        return flow {
            emit(usersApi.postLogin(basicAuth).data)
        }
    }
}