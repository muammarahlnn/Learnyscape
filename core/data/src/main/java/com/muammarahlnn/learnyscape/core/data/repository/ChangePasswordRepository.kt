package com.muammarahlnn.learnyscape.core.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordRepository, 27/03/2024 15.40
 */
interface ChangePasswordRepository {

    fun changePassword(
        oldPassword: String,
        newPassword: String,
    ): Flow<String>
}