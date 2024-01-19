package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ReferenceDetailsResponse, 18/01/2024 17.34
 */
@Serializable
data class ReferenceDetailsResponse(
    val id: String,
    val name: String,
    val description: String?,
    val updatedAt: String,
    @SerialName("attachment") val attachmentUrls: List<String>,
)