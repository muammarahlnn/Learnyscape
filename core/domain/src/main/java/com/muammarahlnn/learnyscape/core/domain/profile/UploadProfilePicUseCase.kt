package com.muammarahlnn.learnyscape.core.domain.profile

import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UploadProfilePicUseCase, 24/11/2023 03.26 by Muammar Ahlan Abimanyu
 */
fun interface UploadProfilePicUseCase {

    operator fun invoke(pic: File): Flow<String>
}