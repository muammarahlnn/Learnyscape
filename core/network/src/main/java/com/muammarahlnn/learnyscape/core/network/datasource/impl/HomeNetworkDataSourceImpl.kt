package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.HomeApi
import com.muammarahlnn.learnyscape.core.network.datasource.HomeNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.ClassInfoResponse
import com.muammarahlnn.learnyscape.core.network.model.response.base.NetworkResult
import com.muammarahlnn.learnyscape.core.network.model.response.base.handleApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNetworkDataSourceImpl, 12/10/2023 22.22 by Muammar Ahlan Abimanyu
 */
@Singleton
class HomeNetworkDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi,
) : HomeNetworkDataSource {
    override fun getClasses(): Flow<NetworkResult<List<ClassInfoResponse>>> = handleApi {
        homeApi.getClasses()
    }
}