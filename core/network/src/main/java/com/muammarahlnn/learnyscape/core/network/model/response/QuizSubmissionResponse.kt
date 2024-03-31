package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSubmissionResponse, 01/02/2024 15.05
 */
@Serializable
data class QuizSubmissionResponse(
    val userId: String,
    val studentName: String,
    val turnInStatus: Boolean,
    val turnedInAt: String,
)