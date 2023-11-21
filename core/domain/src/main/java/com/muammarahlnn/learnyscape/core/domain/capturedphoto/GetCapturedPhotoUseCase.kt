package com.muammarahlnn.learnyscape.core.domain.capturedphoto

import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetCapturedPhotoUseCase, 21/11/2023 21.10 by Muammar Ahlan Abimanyu
 */
fun interface GetCapturedPhotoUseCase : () -> StateFlow<Bitmap?>