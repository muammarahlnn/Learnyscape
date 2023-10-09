package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UsersRepository, 02/10/2023 02.26 by Muammar Ahlan Abimanyu
 */
interface LoginRepository {

    fun postLoginUser(
        username: String,
        password: String,
    ): Flow<Result<LoginModel>>

    fun saveUser(token: String): Flow<Result<UserModel>>

    fun isUserLoggedIn(): Flow<Boolean>
}