package com.muammarahlnn.learnyscape.core.common.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NetworkHandler, 08/11/2023 20.32 by Muammar Ahlan Abimanyu
 */

@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: Error,
)

@Serializable
data class Error(
    @SerialName("code")
    val code: String? = null,

    @SerialName("message")
    val message: String,
)

private val json = Json {
    ignoreUnknownKeys = true
}

internal fun String.convertToErrorResponse() =
    json.decodeFromString<ErrorResponse>(this)

object NoConnectivityException : IOException() {

    override val message: String
        get() = "You aren't connected to the internet"
}