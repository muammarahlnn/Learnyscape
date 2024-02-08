package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TurnInAssignmentSubmissionUseCase, 08/02/2024 02.36
 */
fun interface TurnInAssignmentSubmissionUseCase {

    operator fun invoke(
        submissionId: String,
        turnIn: Boolean,
    ): Flow<String>
}