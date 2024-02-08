package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TurnInTaskSubmissionRequest, 08/02/2024 02.26
 */
@Serializable
data class TurnInTaskSubmissionRequest(
    val turnedIn: Boolean,
)