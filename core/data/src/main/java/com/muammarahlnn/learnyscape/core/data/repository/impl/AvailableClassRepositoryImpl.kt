package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toAvailableClassModels
import com.muammarahlnn.learnyscape.core.data.repository.AvailableClassRepository
import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import com.muammarahlnn.learnyscape.core.network.datasource.AvailableClassNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassRepositoryImpl, 16/11/2023 20.46 by Muammar Ahlan Abimanyu
 */
class AvailableClassRepositoryImpl @Inject constructor(
    private val availableClassNetworkDataSource: AvailableClassNetworkDataSource,
) : AvailableClassRepository {

    override fun getAvailableClasses(): Flow<List<AvailableClassModel>> =
        availableClassNetworkDataSource.getAvailableClasses().map { availableClassResponses ->
            availableClassResponses.toAvailableClassModels()
        }

    override fun requestJoinClass(classId: String): Flow<String> =
        availableClassNetworkDataSource.requestJoinClass(classId)
}