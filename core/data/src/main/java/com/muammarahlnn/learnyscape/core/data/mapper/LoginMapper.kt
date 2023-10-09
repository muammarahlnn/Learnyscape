package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.datastore.model.UserEntity
import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.core.model.data.UserRole
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import com.muammarahlnn.learnyscape.core.network.model.response.UserResponse


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginMapper, 02/10/2023 16.48 by Muammar Ahlan Abimanyu
 */

fun LoginResponse.toLoginModel() = LoginModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
)

fun UserResponse.toUserModel() = UserModel(
    id = id,
    username = username,
    fullName = fullName,
    email = email,
    role = UserRole.getRole(role)
)

fun UserResponse.toUserEntity() = UserEntity(
    id = id,
    username = username,
    fullName = fullName,
    role = role,
)

