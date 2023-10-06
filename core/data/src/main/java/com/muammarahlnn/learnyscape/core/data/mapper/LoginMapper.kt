package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginMapper, 02/10/2023 16.48 by Muammar Ahlan Abimanyu
 */

fun LoginResponse.toLoginModel() = LoginModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
)