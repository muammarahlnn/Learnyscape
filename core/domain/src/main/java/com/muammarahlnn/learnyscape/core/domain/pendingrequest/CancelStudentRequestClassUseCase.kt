package com.muammarahlnn.learnyscape.core.domain.pendingrequest

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CancelStudentRequestClass, 02/03/2024 21.35
 */
fun interface CancelStudentRequestClassUseCase {

    operator fun invoke(classId: String): Flow<String>
}