package com.muammarahlnn.learnyscape.core.domain.resourceoverview

import com.muammarahlnn.learnyscape.core.model.data.AssignmentOverviewModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetAssignmentsUseCase, 17/01/2024 15.49
 */
fun interface GetAssignmentsUseCase {

    operator fun invoke(classId: String): Flow<List<AssignmentOverviewModel>>
}