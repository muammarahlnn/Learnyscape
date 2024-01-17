package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.WaitingListModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestRepository, 17/01/2024 21.40
 */
interface JoinRequestRepository {

    fun getWaitingListClass(classId: String): Flow<List<WaitingListModel>>
}