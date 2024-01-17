package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TaskOverviewResponse, 17/01/2024 15.39
 */
@Serializable
data class TaskOverviewResponse(
    val id: String,
    val name: String,
    val updatedAt: String,
    val dueDate: Long,
)