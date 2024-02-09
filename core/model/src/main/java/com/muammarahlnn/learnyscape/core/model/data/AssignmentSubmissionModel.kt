package com.muammarahlnn.learnyscape.core.model.data

import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionModel, 07/02/2024 01.05
 */
data class AssignmentSubmissionModel(
    val assignmentSubmissionId: String = "",
    val userId: String = "",
    val studentName: String = "",
    val turnInStatus: Boolean = false,
    val attachments: List<File> = listOf(),
)