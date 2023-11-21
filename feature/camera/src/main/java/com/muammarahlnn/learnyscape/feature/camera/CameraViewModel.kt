package com.muammarahlnn.learnyscape.feature.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CameraViewModel, 21/11/2023 19.16 by Muammar Ahlan Abimanyu
 */
class CameraViewModel : ViewModel(){

    private val _takenPhotoBitmap: MutableStateFlow<Bitmap?>  = MutableStateFlow(null)

    val takenPhotoBitmap = _takenPhotoBitmap.asStateFlow()

    fun onPhotoTaken(bitmap: Bitmap) {
        _takenPhotoBitmap.value = bitmap
    }

    fun resetPhoto() {
        _takenPhotoBitmap.value = null
    }
}