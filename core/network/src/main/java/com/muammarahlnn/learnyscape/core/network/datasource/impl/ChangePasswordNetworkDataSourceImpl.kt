package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.ChangePasswordNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import com.muammarahlnn.learnyscape.core.network.model.request.ChangePasswordRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordNetworkDataSourceImpl, 27/03/2024 15.38
 */
@Singleton
class ChangePasswordNetworkDataSourceImpl @Inject constructor(
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi,
) : ChangePasswordNetworkDataSource {

    override fun putChangePassword(oldPassword: String, newPassword: String): Flow<String> = flow {
        emit(
            usersApi.putChangePassword(
                changePasswordRequest = ChangePasswordRequest(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                )
            ).data
        )
    }
}