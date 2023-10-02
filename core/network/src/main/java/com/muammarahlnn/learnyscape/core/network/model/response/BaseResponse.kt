package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file BaseResponse, 01/10/2023 01.30 by Muammar Ahlan Abimanyu
 */

@Serializable
open class BaseResponse<T>(
    @SerialName("status")
    val status: String,

    @SerialName("data")
    val data: T,
)