package com.muammarahlnn.learnyscape.core.datastore.model


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UserEntity, 09/10/2023 16.08 by Muammar Ahlan Abimanyu
 */
data class UserEntity(
    val id: String,
    val username: String,
    val fullName: String,
    val role: String,
)