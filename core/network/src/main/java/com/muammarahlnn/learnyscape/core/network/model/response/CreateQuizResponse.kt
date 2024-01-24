package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateQuizResponse, 23/01/2024 22.06
 */
@Serializable
data class CreateQuizResponse(
    val id: String,
    val message: String,
)