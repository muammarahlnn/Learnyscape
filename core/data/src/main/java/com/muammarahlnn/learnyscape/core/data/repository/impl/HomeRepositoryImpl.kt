package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.data.mapper.toClassesInfoModel
import com.muammarahlnn.learnyscape.core.data.mapper.toUserModel
import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.model.data.ClassInfoModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.core.network.datasource.HomeNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.base.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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

    override fun getClasses(): Flow<Result<List<ClassInfoModel>>> =
        homeNetworkDataSource.getClasses().map { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> Result.Success(
                    data = networkResult.data.toClassesInfoModel()
                )
                is NetworkResult.Error -> Result.Error(
                    code = networkResult.code,
                    message = networkResult.message,
                )
                is NetworkResult.Exception -> Result.Exception(
                    exception = networkResult.exception,
                )
            }
        }.onStart {
            emit(Result.Loading)
        }
}