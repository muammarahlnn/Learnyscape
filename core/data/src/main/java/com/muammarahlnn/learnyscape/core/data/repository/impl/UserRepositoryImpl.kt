package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.repository.UserRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UserRepository, 08/10/2023 20.37 by Muammar Ahlan Abimanyu
 */
class UserRepositoryImpl @Inject constructor(
    private val learnyscapePreferences: LearnyscapePreferencesDataSource
) : UserRepository {

    override fun isUserLoggedIn(): Flow<Boolean> =
        learnyscapePreferences.getAccessToken().map { accessToken ->
            accessToken.isNotEmpty()
        }
}