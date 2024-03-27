package com.muammarahlnn.learnyscape.core.network.datasource

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordNetworkDataSource, 27/03/2024 15.38
 */
interface ChangePasswordNetworkDataSource {

    fun putChangePassword(
        oldPassword: String,
        newPassword: String,
    ): Flow<String>
}