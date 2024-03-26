package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.ClassesApi
import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.AvailableClassNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import com.muammarahlnn.learnyscape.core.network.model.request.RequestJoinClassRequest
import com.muammarahlnn.learnyscape.core.network.model.response.AvailableClassResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassNetworkDataSourceImpl, 16/11/2023 20.05 by Muammar Ahlan Abimanyu
 */
@Singleton
class AvailableClassNetworkDataSourceImpl @Inject constructor(
    private val classesApi: ClassesApi,
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi,
) : AvailableClassNetworkDataSource {

    override fun getAvailableClasses(searchQuery: String): Flow<List<AvailableClassResponse>> =
        flow {
            emit(classesApi.getAvailableClasses(searchQuery).data)
        }

    override fun requestJoinClass(classId: String): Flow<String> = flow {
        emit(
            usersApi.putRequestJoinClass(
                RequestJoinClassRequest(classId)
            ).data
        )
    }
}