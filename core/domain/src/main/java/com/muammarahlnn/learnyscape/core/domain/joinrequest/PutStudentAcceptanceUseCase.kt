package com.muammarahlnn.learnyscape.core.domain.joinrequest

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PutStudentAcceptanceUseCase, 18/01/2024 13.09
 */
fun interface PutStudentAcceptanceUseCase {

    operator fun invoke(
        studentId: String,
        accepted: Boolean
    ): Flow<String>
}