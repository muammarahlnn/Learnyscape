package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordRequest, 27/03/2024 15.37
 */
@Serializable
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)