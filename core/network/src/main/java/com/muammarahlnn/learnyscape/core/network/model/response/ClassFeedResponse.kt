package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedResponse, 27/01/2024 11.39
 */
@Serializable
data class ClassFeedResponse(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val type: FeedType,
    val uri: String,
    @SerialName("thumbnail") val profilePicUrl: String? = null,
) {

    enum class FeedType {
        ANNOUNCEMENT,
        REFERENCE,
        TASK,
        QUIZ,
    }
}