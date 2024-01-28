package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedRepository, 27/01/2024 12.18
 */
interface ClassFeedRepository {

    fun getClassFeeds(classId: String): Flow<List<ClassFeedModel>>
}