package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toUserModel
import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeRepositoryImpl, 09/10/2023 22.32 by Muammar Ahlan Abimanyu
 */
class HomeRepositoryImpl @Inject constructor(
    private val learnyscapePreferences: LearnyscapePreferencesDataSource
) : HomeRepository {
    override fun getLoggedInUser(): Flow<UserModel> =
        learnyscapePreferences.getUser().map { userEntity ->
            userEntity.toUserModel()
        }
}