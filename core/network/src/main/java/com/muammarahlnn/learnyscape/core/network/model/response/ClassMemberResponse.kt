package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassMemberResponse, 16/11/2023 19.54 by Muammar Ahlan Abimanyu
 */
@Serializable
data class ClassMemberResponse(
    val id: String,
    @SerialName("fullname") val fullName: String,
    val username: String,
)