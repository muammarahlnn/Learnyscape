package com.muammarahlnn.learnyscape.core.domain.classfeed

import com.muammarahlnn.learnyscape.core.model.data.ClassDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetClassDetailsUseCase, 25/02/2024 10.59
 */
fun interface GetClassDetailsUseCase {

    operator fun invoke(classId: String): Flow<ClassDetailsModel>
}