package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassesResponse, 12/10/2023 22.04 by Muammar Ahlan Abimanyu
 */
@Serializable
data class ClassInfoResponse(
    @SerialName("id")
    val id: String,

    @SerialName("className")
    val className: String,

    @SerialName("lecturerNames")
    val lecturerNames: List<String>,
)