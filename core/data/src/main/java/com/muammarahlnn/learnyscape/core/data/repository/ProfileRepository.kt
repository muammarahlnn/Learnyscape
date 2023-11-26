package com.muammarahlnn.learnyscape.core.data.repository

import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileRepository, 09/10/2023 19.17 by Muammar Ahlan Abimanyu
 */
interface ProfileRepository {

    suspend fun logout()

    fun uploadProfilePic(pic: File): Flow<String>
}