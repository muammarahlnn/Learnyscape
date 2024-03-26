package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.AvailableClassResponse
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassNetworkDataSource, 16/11/2023 20.04 by Muammar Ahlan Abimanyu
 */
interface AvailableClassNetworkDataSource {

    fun getAvailableClasses(searchQuery: String): Flow<List<AvailableClassResponse>>

    fun requestJoinClass(classId: String): Flow<String>
}