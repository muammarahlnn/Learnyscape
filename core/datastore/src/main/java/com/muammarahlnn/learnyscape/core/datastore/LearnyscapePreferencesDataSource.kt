package com.muammarahlnn.learnyscape.core.datastore

import com.muammarahlnn.learnyscape.core.datastore.model.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LearnyscapePreferencesDataStore, 31/05/2024 19.28
 */
interface LearnyscapePreferencesDataSource {

    suspend fun saveUser(user: UserEntity)

    suspend fun removeUser()

    fun getUser(): Flow<UserEntity>

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    fun getAccessToken(): Flow<String>

    fun getRefreshToken(): Flow<String>
}