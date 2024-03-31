package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentTaskSubmissionResponse, 07/02/2024 01.00
 */
@Serializable
data class StudentTaskSubmissionResponse(
    val userId: String,
    val taskSubmissionId: String,
    val studentName: String,
    val turnInStatus: Boolean,
    val turnedInAt: String? = null,
    @SerialName("attachment") val attachmentUrls: List<String>,
)