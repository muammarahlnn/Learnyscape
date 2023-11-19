package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toEnrolledClassInfoModels
import com.muammarahlnn.learnyscape.core.data.mapper.toUserModel
import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.core.network.datasource.HomeNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeRepositoryImpl, 09/10/2023 22.32 by Muammar Ahlan Abimanyu
 */
class HomeRepositoryImpl @Inject constructor(
    private val learnyscapePreferences: LearnyscapePreferencesDataSource,
    private val homeNetworkDataSource: HomeNetworkDataSource,
) : HomeRepository {

    override fun getLoggedInUser(): Flow<UserModel> =
        learnyscapePreferences.getUser().map { userEntity ->
            userEntity.toUserModel()
        }

    override fun getEnrolledClasses(): Flow<List<EnrolledClassInfoModel>> =
        homeNetworkDataSource.getEnrolledClasses().map { enrolledClassInfoResponses ->
            enrolledClassInfoResponses.toEnrolledClassInfoModels()
        }
}