package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File UploadAssignmentAttachmentUseCase, 05/02/2024 23.30
 */
fun interface UploadAssignmentAttachmentsUseCase {

    operator fun invoke(
        assignmentId: String,
        attachments: List<File>,
    ): Flow<String>
}