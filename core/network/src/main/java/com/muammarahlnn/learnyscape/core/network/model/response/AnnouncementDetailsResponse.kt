package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementDetailsResponse, 31/01/2024 16.05
 */
@Serializable
data class AnnouncementDetailsResponse(
    val id: String,
    val authorName: String,
    val updatedAt: String,
    val description: String?,
    @SerialName("attachment") val attachmentUrls: List<String>,
)