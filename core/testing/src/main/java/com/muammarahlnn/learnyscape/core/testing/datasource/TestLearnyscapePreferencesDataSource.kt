package com.muammarahlnn.learnyscape.core.testing.datasource

import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.datastore.model.UserEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TestLearnyscapePreferencesDataSource, 31/05/2024 19.23
 */
class TestLearnyscapePreferencesDataSource : LearnyscapePreferencesDataSource {

    private val userEntityFlow: MutableSharedFlow<UserEntity> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val accessTokenFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val refreshTokenFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun saveUser(user: UserEntity) {
        userEntityFlow.tryEmit(user)
    }

    override suspend fun removeUser() {
        userEntityFlow.tryEmit(
            UserEntity(
                id = "",
                username = "",
                fullName = "",
                role = "",
            )
        )
    }

    override fun getUser(): Flow<UserEntity> = userEntityFlow

    override suspend fun saveAccessToken(accessToken: String) {
        accessTokenFlow.tryEmit(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        refreshTokenFlow.tryEmit(refreshToken)
    }

    override fun getAccessToken(): Flow<String> = accessTokenFlow

    override fun getRefreshToken(): Flow<String> = refreshTokenFlow
}