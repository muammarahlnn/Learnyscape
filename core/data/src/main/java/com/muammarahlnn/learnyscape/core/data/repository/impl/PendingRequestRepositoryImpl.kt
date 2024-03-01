package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toPendingRequestModels
import com.muammarahlnn.learnyscape.core.data.repository.PendingRequestRepository
import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel
import com.muammarahlnn.learnyscape.core.network.datasource.PendingRequestNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestRepositoryImpl, 29/02/2024 22.13
 */
class PendingRequestRepositoryImpl @Inject constructor(
    private val pendingRequestNetworkDataSource: PendingRequestNetworkDataSource,
) : PendingRequestRepository {

    override fun getStudentPendingRequestClasses(): Flow<List<PendingRequestModel>> =
        pendingRequestNetworkDataSource.getStudentPendingRequestClasses().map { pendingRequestRepsonses ->
            pendingRequestRepsonses.toPendingRequestModels()
        }
}