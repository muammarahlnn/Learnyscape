package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestModel, 29/02/2024 22.12
 */
data class PendingRequestModel(
    val id: String,
    val classId: String,
    val className: String,
    val lecturerNames: List<String>,
)