package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DeleteAssignmentUseCase, 29/01/2024 21.45
 */
fun interface DeleteAssignmentUseCase {

    operator fun invoke(assignmentId: String): Flow<String>
}