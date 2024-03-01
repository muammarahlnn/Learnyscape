package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestRepository, 29/02/2024 22.06
 */
interface PendingRequestRepository {

    fun getStudentPendingRequestClasses(): Flow<List<PendingRequestModel>>
}