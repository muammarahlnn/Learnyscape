package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetAssignmentSubmissionsUseCase, 31/01/2024 20.54
 */
fun interface GetAssignmentSubmissionsUseCase {

    operator fun invoke(assignmentId: String): Flow<List<AssignmentSubmissionModel>>
}