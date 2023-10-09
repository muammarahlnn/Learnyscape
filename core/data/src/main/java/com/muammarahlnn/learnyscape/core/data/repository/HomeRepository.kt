package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeRepository, 09/10/2023 22.30 by Muammar Ahlan Abimanyu
 */
interface HomeRepository {

    fun getLoggedInUser(): Flow<UserModel>
}