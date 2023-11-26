package com.muammarahlnn.learnyscape.core.domain.profile

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetProfilePicUseCase, 26/11/2023 23.03 by Muammar Ahlan Abimanyu
 */
fun interface GetProfilePicUseCase : () -> Flow<Bitmap?>