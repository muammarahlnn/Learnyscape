package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentAcceptanceRequest, 18/01/2024 13.04
 */
@Serializable
data class StudentAcceptanceRequest(
    val status: Boolean
)