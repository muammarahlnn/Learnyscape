package com.muammarahlnn.learnyscape.core.domain.profile

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetProfilePicByIdUseCase, 26/01/2024 21.21
 */
fun interface GetProfilePicByIdUseCase {

    operator fun invoke(userId: String): Flow<Bitmap?>
}