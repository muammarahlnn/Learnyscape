package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EditQuizUseCase, 01/04/2024 13.14
 */
fun interface EditQuizUseCase {

    operator fun invoke(
        quizId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int
    ): Flow<String>
}