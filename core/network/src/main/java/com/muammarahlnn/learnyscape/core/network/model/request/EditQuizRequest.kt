package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EditQuizRequest, 01/04/2024 12.56
 */
@Serializable
data class EditQuizRequest(
    val name: String,
    val description: String?,
    val quizType: String,
    val duration: Int,
    val startDate: Long,
    val endDate: Long
)