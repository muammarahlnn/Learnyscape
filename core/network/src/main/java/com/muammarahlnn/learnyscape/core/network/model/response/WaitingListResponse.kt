package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File WaitingListResponse, 17/01/2024 21.29
 */
@Serializable
data class WaitingListResponse(
    val id: String,
    val userId: String,
    val studentId: String,
    @SerialName("fullname") val fullName: String,
    val status: WaitingListStatusResponse,
)

enum class WaitingListStatusResponse {
    PENDING,
    ACCEPTED,
}