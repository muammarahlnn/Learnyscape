package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file RequestJoinClassRequest, 20/11/2023 16.12 by Muammar Ahlan Abimanyu
 */
@Serializable
data class RequestJoinClassRequest(
    val classId: String
)