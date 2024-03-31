package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionModel, 31/01/2024 20.46
 */
data class StudentSubmissionModel(
    val id: String,
    val userId: String,
    val studentName: String,
    val turnInStatus: Boolean,
    val turnedInAt: String,
)