package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetAssignmentDetailsUseCase, 29/01/2024 21.44
 */
fun interface GetAssignmentDetailsUseCase {

    operator fun invoke(assignmentId: String): Flow<AssignmentDetailsModel>
}