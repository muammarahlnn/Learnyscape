package com.muammarahlnn.learnyscape.core.domain.submissiondetails

import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetAssignmentSubmissionDetailsUseCase, 13/02/2024 23.31
 */
fun interface GetAssignmentSubmissionDetailsUseCase {

    operator fun invoke(submissionId: String): Flow<AssignmentSubmissionModel>
}