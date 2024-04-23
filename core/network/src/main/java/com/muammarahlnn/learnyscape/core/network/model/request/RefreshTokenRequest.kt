package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File RefreshTokenRequest, 23/04/2024 16.17
 */
@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)