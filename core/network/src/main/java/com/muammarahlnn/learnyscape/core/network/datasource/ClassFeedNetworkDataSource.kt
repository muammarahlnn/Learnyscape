package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.ClassDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ClassFeedResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedNetworkDataSource, 27/01/2024 12.16
 */
interface ClassFeedNetworkDataSource {

    fun getClassFeeds(classId: String): Flow<List<ClassFeedResponse>>

    fun getClassDetails(classId: String): Flow<ClassDetailsResponse>
}