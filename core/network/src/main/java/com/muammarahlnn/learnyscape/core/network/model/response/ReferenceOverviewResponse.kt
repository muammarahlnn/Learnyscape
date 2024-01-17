package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ReferenceOverviewResponse, 17/01/2024 15.38
 */
@Serializable
data class ReferenceOverviewResponse(
    val id: String,
    val name: String,
    val updatedAt: String,
)