package com.muammarahlnn.learnyscape.core.network.model.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ErrorResponse, 06/10/2023 16.39 by Muammar Ahlan Abimanyu
 */

@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: Error,
)

@Serializable
data class Error(
    @SerialName("code")
    val code: String,

    @SerialName("message")
    val message: String,
)

private val json = Json {
    ignoreUnknownKeys = true
}

internal fun String.convertToErrorResponse() =
    json.decodeFromString<ErrorResponse>(this)