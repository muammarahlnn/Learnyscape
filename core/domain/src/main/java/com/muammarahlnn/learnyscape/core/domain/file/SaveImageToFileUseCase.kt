package com.muammarahlnn.learnyscape.core.domain.file

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SaveImageToFileUseCase, 24/11/2023 18.50 by Muammar Ahlan Abimanyu
 */
fun interface SaveImageToFileUseCase {

    operator fun invoke(image: Bitmap): Flow<File?>
}