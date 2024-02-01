package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TaskSubmissionResponse, 31/01/2024 20.33
 */
@Serializable
data class TaskSubmissionResponse(
    @SerialName("taskSubmissionId") val id: String?,
    val userId: String,
    val studentName: String,
    val turnInStatus: Boolean,
)