package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizDetailsResponse, 31/01/2024 01.44
 */
@Serializable
data class QuizDetailsResponse(
    val id: String,
    val name: String,
    val updatedAt: String,
    val description: String?,
    val startDate: Long,
    val endDate: Long,
    val duration: Int,
    val type: QuizType,
) {

    enum class QuizType {
        MULTIPLE_CHOICE,
        PHOTO_ANSWER
    }
}