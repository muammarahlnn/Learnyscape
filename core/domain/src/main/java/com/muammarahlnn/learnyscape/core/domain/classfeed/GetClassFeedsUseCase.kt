package com.muammarahlnn.learnyscape.core.domain.classfeed

import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetClassFeedsUseCase, 27/01/2024 12.30
 */
fun interface GetClassFeedsUseCase {

    operator fun invoke(classId: String): Flow<List<ClassFeedModel>>
}