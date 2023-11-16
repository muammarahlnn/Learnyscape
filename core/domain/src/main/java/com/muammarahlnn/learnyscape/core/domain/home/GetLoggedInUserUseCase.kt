package com.muammarahlnn.learnyscape.core.domain.home

import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetLoggedInUserUseCase, 09/10/2023 22.37 by Muammar Ahlan Abimanyu
 */
fun interface GetLoggedInUserUseCase : () -> Flow<UserModel>