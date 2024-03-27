package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.repository.ChangePasswordRepository
import com.muammarahlnn.learnyscape.core.network.datasource.ChangePasswordNetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordRepositoryImpl, 27/03/2024 15.41
 */
class ChangePasswordRepositoryImpl @Inject constructor(
    private val changePasswordNetworkDataSource: ChangePasswordNetworkDataSource,
) : ChangePasswordRepository {

    override fun changePassword(oldPassword: String, newPassword: String): Flow<String> =
        changePasswordNetworkDataSource.putChangePassword(
            oldPassword = oldPassword,
            newPassword = newPassword,
        )
}