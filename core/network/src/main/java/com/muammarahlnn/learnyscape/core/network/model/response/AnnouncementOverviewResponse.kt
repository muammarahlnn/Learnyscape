package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementOverviewResponse, 17/01/2024 14.55
 */
@Serializable
data class AnnouncementOverviewResponse(
    val id: String,
    val name: String,
    val updatedAt: String,
    val authorName: String,
)