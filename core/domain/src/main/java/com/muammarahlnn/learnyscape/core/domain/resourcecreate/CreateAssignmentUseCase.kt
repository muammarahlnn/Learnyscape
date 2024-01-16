package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateAssignmentUseCase, 16/01/2024 13.33
 */
fun interface CreateAssignmentUseCase {

    operator fun invoke(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>,
    ): Flow<String>
}