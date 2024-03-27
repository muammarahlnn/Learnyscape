package com.muammarahlnn.learnyscape.core.domain.changepassword

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordUseCase, 27/03/2024 15.43
 */
fun interface ChangePasswordUseCase {

    operator fun invoke(
        oldPassword: String,
        newPassword: String,
    ): Flow<String>
}