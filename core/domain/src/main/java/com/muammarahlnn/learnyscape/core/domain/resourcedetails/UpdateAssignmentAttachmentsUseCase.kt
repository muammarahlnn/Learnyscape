package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File UpdateAssignmentAttachmentsUseCase, 08/02/2024 01.52
 */
fun interface UpdateAssignmentAttachmentsUseCase {

    operator fun invoke(
        submissionId: String,
        attachments: List<File>,
    ): Flow<String>
}