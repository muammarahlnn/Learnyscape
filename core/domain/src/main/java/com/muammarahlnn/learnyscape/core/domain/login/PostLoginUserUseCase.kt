package com.muammarahlnn.learnyscape.core.domain.login

import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file PostLoginUserUseCase, 02/10/2023 16.31 by Muammar Ahlan Abimanyu
 */
fun interface PostLoginUserUseCase {

    operator fun invoke(
        username: String,
        password: String,
    ): Flow<LoginModel>
}