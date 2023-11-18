package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AvailableClassApi
import com.muammarahlnn.learnyscape.core.network.datasource.AvailableClassNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.AvailableClassResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassNetworkDataSourceImpl, 16/11/2023 20.05 by Muammar Ahlan Abimanyu
 */
@Singleton
class AvailableClassNetworkDataSourceImpl @Inject constructor(
    private val availableClassApi: AvailableClassApi
) : AvailableClassNetworkDataSource {

    override fun getAvailableClasses(): Flow<List<AvailableClassResponse>> = flow {
        emit(availableClassApi.getAvailableClasses().data)
    }
}