package com.muammarahlnn.learnyscape.core.data.repository.impl

import android.graphics.Bitmap
import com.muammarahlnn.learnyscape.core.data.repository.CapturedPhotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CapturedPhotoRepositoryImpl, 21/11/2023 20.31 by Muammar Ahlan Abimanyu
 */
class CapturedPhotoRepositoryImpl @Inject constructor() : CapturedPhotoRepository {

    private val takenPhotoBitmap = MutableStateFlow<Bitmap?>(null)

    override fun getPhoto(): StateFlow<Bitmap?> = takenPhotoBitmap.asStateFlow()

    override suspend fun saveCapturedPhoto(photo: Bitmap) {
        takenPhotoBitmap.update { photo }
    }

    override suspend fun resetPhoto() {
        takenPhotoBitmap.update { null }
    }
}