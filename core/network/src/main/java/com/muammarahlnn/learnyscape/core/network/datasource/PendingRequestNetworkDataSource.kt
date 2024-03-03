package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.PendingRequestResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestDataSource, 29/02/2024 21.57
 */
interface PendingRequestNetworkDataSource {

    fun getStudentPendingRequestClasses(): Flow<List<PendingRequestResponse>>

    fun cancelStudentRequestClass(classId: String): Flow<String>
}