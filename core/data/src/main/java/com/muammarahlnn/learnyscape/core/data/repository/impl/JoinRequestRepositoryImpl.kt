package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toWaitingListModels
import com.muammarahlnn.learnyscape.core.data.repository.JoinRequestRepository
import com.muammarahlnn.learnyscape.core.model.data.WaitingListModel
import com.muammarahlnn.learnyscape.core.network.datasource.JoinRequestNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestRepositoryImpl, 17/01/2024 21.56
 */
class JoinRequestRepositoryImpl @Inject constructor(
    private val joinRequestNetworkDataSource: JoinRequestNetworkDataSource,
) : JoinRequestRepository {

    override fun getWaitingListClass(classId: String): Flow<List<WaitingListModel>> =
        joinRequestNetworkDataSource.getWaitingListClass(classId).map { waitingClassResponses ->
            waitingClassResponses.toWaitingListModels()
        }
}