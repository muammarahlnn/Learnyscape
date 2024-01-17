package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.WaitingListResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestNetworkDataSource, 17/01/2024 21.32
 */
interface JoinRequestNetworkDataSource {

    fun getWaitingListClass(classId: String): Flow<List<WaitingListResponse>>
}