package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File UploadProfilePicResponse, 01/02/2024 22.23
 */
@Serializable
data class UploadProfilePicResponse(
    val name: String,
    val path: String,
)