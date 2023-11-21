package com.muammarahlnn.learnyscape.core.data.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CapturedPhotoRepository, 21/11/2023 20.31 by Muammar Ahlan Abimanyu
 */
interface CapturedPhotoRepository {

    fun getPhoto(): StateFlow<Bitmap?>

    suspend fun saveCapturedPhoto(photo: Bitmap)

    suspend fun resetPhoto()
}