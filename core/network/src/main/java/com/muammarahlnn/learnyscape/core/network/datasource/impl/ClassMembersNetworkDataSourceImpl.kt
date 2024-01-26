package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.ClassMembersNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import com.muammarahlnn.learnyscape.core.network.model.response.ClassMembersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersNetworkDataSourceImpl, 25/01/2024 20.21
 */
@Singleton
class ClassMembersNetworkDataSourceImpl @Inject constructor(
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi,
) : ClassMembersNetworkDataSource {

    override fun getClassMembers(classId: String): Flow<ClassMembersResponse> = flow {
        emit(usersApi.getClassMembers(classId).data)
    }

}