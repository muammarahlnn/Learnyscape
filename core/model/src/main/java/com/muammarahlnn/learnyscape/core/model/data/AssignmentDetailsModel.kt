package com.muammarahlnn.learnyscape.core.model.data

import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentDetailsModel, 29/01/2024 21.30
 */
data class AssignmentDetailsModel(
    val id: String,
    val name: String,
    val description: String,
    val updatedAt: String,
    val dueDate: String,
    val attachments: List<File>,
)