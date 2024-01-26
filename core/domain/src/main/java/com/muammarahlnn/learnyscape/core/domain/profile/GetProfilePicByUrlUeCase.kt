package com.muammarahlnn.learnyscape.core.domain.profile

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetProfilePicByUrlUeCase, 26/01/2024 14.05
 */
fun interface GetProfilePicByUrlUeCase {

    operator fun invoke(profilePicUrl: String): Flow<Bitmap?>
}