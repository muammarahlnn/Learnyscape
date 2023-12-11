package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.SchedulesNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import com.muammarahlnn.learnyscape.core.network.model.response.ScheduleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SchedulesDataSourceImpl, 08/12/2023 22.19
 */
@Singleton
class SchedulesNetworkDataSourceImpl @Inject constructor(
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi,
) : SchedulesNetworkDataSource {

    override fun getSchedules(): Flow<List<ScheduleResponse>> = flow {
        emit(usersApi.getSchedules().data)
    }
}