package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionModel, 31/01/2024 20.46
 */
data class AssignmentSubmissionModel(
    val id: String,
    val userId: String,
    val studentName: String,
    val turnInStatus: Boolean,
)