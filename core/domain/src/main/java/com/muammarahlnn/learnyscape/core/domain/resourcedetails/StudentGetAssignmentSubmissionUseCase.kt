package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.AssignmentSubmissionModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentGetAssignmentSubmissionUseCase, 07/02/2024 01.22
 */
fun interface StudentGetAssignmentSubmissionUseCase {

    operator fun invoke(assignmentId: String): Flow<AssignmentSubmissionModel>
}