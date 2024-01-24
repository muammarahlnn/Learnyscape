package com.muammarahlnn.learnyscape.core.domain.resourceoverview

import com.muammarahlnn.learnyscape.core.model.data.QuizOverviewModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetQuizzesUseCase, 24/01/2024 20.29
 */
fun interface GetQuizzesUseCase {

    operator fun invoke(classId: String): Flow<List<QuizOverviewModel>>
}