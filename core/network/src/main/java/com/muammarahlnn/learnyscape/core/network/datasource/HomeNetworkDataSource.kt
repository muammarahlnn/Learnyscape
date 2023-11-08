package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.ClassInfoResponse
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeNetworkDataSource, 12/10/2023 22.20 by Muammar Ahlan Abimanyu
 */
interface HomeNetworkDataSource {

    fun getClasses(): Flow<List<ClassInfoResponse>>
}