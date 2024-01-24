package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateQuizRequest, 17/01/2024 14.33
 */
@Serializable
data class CreateQuizRequest(
    val classId: String,
    val name: String,
    val description: String?,
    val quizType: String,
    val duration: Int,
    val startDate: Long,
    val endDate: Long
)
