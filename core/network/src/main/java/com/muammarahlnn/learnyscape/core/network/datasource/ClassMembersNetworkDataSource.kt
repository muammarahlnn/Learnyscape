package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.ClassMembersResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersNetworkDataSource, 25/01/2024 20.21
 */
interface ClassMembersNetworkDataSource {

    fun getClassMembers(classId: String): Flow<ClassMembersResponse>
}