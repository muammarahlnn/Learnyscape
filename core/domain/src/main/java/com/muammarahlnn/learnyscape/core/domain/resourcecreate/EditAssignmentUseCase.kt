package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EditAssignmentUseCase, 01/04/2024 13.14
 */
fun interface EditAssignmentUseCase {

    operator fun invoke(
        assignmentId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>,
    ): Flow<String>
}