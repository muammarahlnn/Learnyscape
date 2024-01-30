package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TaskDetailsResponse, 29/01/2024 19.30
 */
@Serializable
data class TaskDetailsResponse(
    val id: String,
    val name: String,
    val description: String?,
    val updatedAt: String,
    val dueDate: Long,
    @SerialName("attachment") val attachmentUrls: List<String>,
)