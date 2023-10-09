package com.muammarahlnn.learnyscape.core.data.repository

import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UserRepository, 08/10/2023 20.36 by Muammar Ahlan Abimanyu
 */
interface UserRepository {

    fun isUserLoggedIn(): Flow<Boolean>
}