package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileRepositoryImpl, 09/10/2023 19.18 by Muammar Ahlan Abimanyu
 */
class ProfileRepositoryImpl @Inject constructor(
    private val learnyscapePreferences: LearnyscapePreferencesDataSource
) : ProfileRepository {

    override suspend fun logout() {
        learnyscapePreferences.removeUser()
    }
}