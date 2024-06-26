package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toLoginModel
import com.muammarahlnn.learnyscape.core.data.mapper.toUserEntity
import com.muammarahlnn.learnyscape.core.data.mapper.toUserModel
import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.core.network.datasource.LoginNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginRepositoryImpl, 02/10/2023 02.29 by Muammar Ahlan Abimanyu
 */
class LoginRepositoryImpl @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource,
    private val learnyscapePreferences: LearnyscapePreferencesDataSource,
) : LoginRepository {

    override fun postLoginUser(
        username: String,
        password: String
    ): Flow<LoginModel> =
        loginNetworkDataSource.postLogin(username, password).map { loginResponse ->
            loginResponse.toLoginModel()
        }

    override fun saveUser(accessToken: String, refreshToken: String): Flow<UserModel> =
        loginNetworkDataSource.getCredential(accessToken).map { userResponse ->
            learnyscapePreferences.saveAccessToken(accessToken)
            learnyscapePreferences.saveRefreshToken(refreshToken)
            learnyscapePreferences.saveUser(
                userResponse.toUserEntity()
            )

            userResponse.toUserModel()
        }

    override fun isUserLoggedIn(): Flow<Boolean> =
        learnyscapePreferences.getAccessToken().map { accessToken ->
            accessToken.isNotEmpty()
        }
}