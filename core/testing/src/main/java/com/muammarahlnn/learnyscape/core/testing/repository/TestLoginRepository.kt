package com.muammarahlnn.learnyscape.core.testing.repository

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.annotations.TestOnly

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TestLoginRepository, 26/05/2024 23.17
 */
class TestLoginRepository : LoginRepository {

    private val loginModelFlow: MutableSharedFlow<LoginModel> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val userModelFlow: MutableSharedFlow<UserModel> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val isUserLoggedInFlow: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun postLoginUser(username: String, password: String): Flow<LoginModel> = loginModelFlow

    override fun saveUser(accessToken: String, refreshToken: String): Flow<UserModel> = userModelFlow

    override fun isUserLoggedIn(): Flow<Boolean> = isUserLoggedInFlow

    @TestOnly
    fun userLogin() {
        isUserLoggedInFlow.tryEmit(true)
    }

    @TestOnly
    fun userLogout() {
        isUserLoggedInFlow.tryEmit(false)
    }
}