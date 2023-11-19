package com.muammarahlnn.learnyscape.core.domain.login

import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SaveUserUseCase, 09/10/2023 15.47 by Muammar Ahlan Abimanyu
 */
fun interface SaveUserUseCase {

    operator fun invoke(token: String): Flow<UserModel>
}