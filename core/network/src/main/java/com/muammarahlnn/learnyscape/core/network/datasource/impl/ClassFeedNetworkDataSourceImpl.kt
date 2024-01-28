package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.ClassesApi
import com.muammarahlnn.learnyscape.core.network.datasource.ClassFeedNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.ClassFeedResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedNetworkDataSourceImpl, 27/01/2024 12.17
 */
@Singleton
class ClassFeedNetworkDataSourceImpl @Inject constructor(
    private val classesApi: ClassesApi,
) : ClassFeedNetworkDataSource {

    override fun getClassFeeds(classId: String): Flow<List<ClassFeedResponse>> = flow {
        emit(classesApi.getClassHistories(classId).data)
    }
}