package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestResponse, 29/02/2024 21.45
 */
@Serializable
data class PendingRequestResponse(
    val id: String,
    val classId: String,
    val className: String,
    val lecturers: List<String>,
)