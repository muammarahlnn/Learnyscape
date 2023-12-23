package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UserResponse, 07/10/2023 21.42 by Muammar Ahlan Abimanyu
 */
@Serializable
data class UserResponse(
    @SerialName("userId")
    val id: String,

    @SerialName("username")
    val username: String,

    @SerialName("fullname")
    val fullName: String,

    @SerialName("email")
    val email: String? = null,

    @SerialName("role")
    val role: String,
)