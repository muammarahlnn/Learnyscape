package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizOverviewResponse, 24/01/2024 19.21
 */
@Serializable
data class QuizOverviewResponse(
    val id: String,
    val name: String,
    val updatedAt: String,
    val startDate: Long,
)