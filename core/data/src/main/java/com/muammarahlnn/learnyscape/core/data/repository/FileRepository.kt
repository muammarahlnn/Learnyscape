package com.muammarahlnn.learnyscape.core.data.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file FileRepository, 24/11/2023 15.46 by Muammar Ahlan Abimanyu
 */
interface FileRepository {

    fun saveImageToFile(image: Bitmap): Flow<File?>
}