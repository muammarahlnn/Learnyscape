package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsNetworkDataSource, 18/01/2024 17.38
 */
interface ResourceDetailsNetworkDataSource {

    fun getReferenceDetails(referenceId: String): Flow<ReferenceDetailsResponse>
}