package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import com.muammarahlnn.learnyscape.core.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginNetworkDataSource, 02/10/2023 11.51 by Muammar Ahlan Abimanyu
 */
interface LoginNetworkDataSource {

    fun postLogin(
        username: String,
        password: String,
    ): Flow<LoginResponse>

    fun getCredential(token: String): Flow<UserResponse>
}