package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationResponse, 26/03/2024 23.10
 */
@Serializable
data class NotificationResponse(
    val id: String,
    val createdAt: String,
    val uri: String,
    val title: String,
    val description: String,
    val type: ClassFeedTypeResponse,
)