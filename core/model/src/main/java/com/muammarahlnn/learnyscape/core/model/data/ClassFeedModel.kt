package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedModel, 27/01/2024 12.24
 */
data class ClassFeedModel(
    val id: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val type: FeedType,
    val uri: String,
    val description: String,
    val profilePicUrl: String,
) {

    enum class FeedType {
        ANNOUNCEMENT,
        MODULE,
        ASSIGNMENT,
        QUIZ,
    }
}