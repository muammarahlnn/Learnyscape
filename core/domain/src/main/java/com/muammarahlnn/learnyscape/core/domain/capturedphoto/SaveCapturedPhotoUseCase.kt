package com.muammarahlnn.learnyscape.core.domain.capturedphoto

import android.graphics.Bitmap


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SaveCapturedPhotoUseCase, 21/11/2023 21.09 by Muammar Ahlan Abimanyu
 */
fun interface SaveCapturedPhotoUseCase {

    suspend operator fun invoke(photo: Bitmap)
}