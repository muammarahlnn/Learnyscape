package com.muammarahlnn.learnyscape.core.domain.login

import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file IsUserLoggedInUseCase, 08/10/2023 20.33 by Muammar Ahlan Abimanyu
 */
fun interface IsUserLoggedInUseCase : () -> Flow<Boolean>