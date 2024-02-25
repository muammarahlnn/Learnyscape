package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toClassDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toClassFeedModels
import com.muammarahlnn.learnyscape.core.data.repository.ClassFeedRepository
import com.muammarahlnn.learnyscape.core.model.data.ClassDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.network.datasource.ClassFeedNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedRepositoryImpl, 27/01/2024 12.28
 */
class ClassFeedRepositoryImpl @Inject constructor(
    private val classFeedNetworkDataSource: ClassFeedNetworkDataSource,
) : ClassFeedRepository {

    override fun getClassFeeds(classId: String): Flow<List<ClassFeedModel>> =
        classFeedNetworkDataSource.getClassFeeds(classId).map { classFeedResponses ->
            classFeedResponses.toClassFeedModels()
        }

    override fun getClassDetails(classId: String): Flow<ClassDetailsModel> =
        classFeedNetworkDataSource.getClassDetails(classId).map { classDetailsResponse ->
            classDetailsResponse.toClassDetailsModel()
        }
}