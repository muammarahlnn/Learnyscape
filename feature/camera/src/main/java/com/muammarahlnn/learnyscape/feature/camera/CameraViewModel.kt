package com.muammarahlnn.learnyscape.feature.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.SaveCapturedPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CameraViewModel, 21/11/2023 19.16 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class CameraViewModel @Inject constructor(
    private val saveCapturedPhotoUseCase: SaveCapturedPhotoUseCase,
) : ViewModel() {

    private val _takenPhotoBitmap: MutableStateFlow<Bitmap?>  = MutableStateFlow(null)

    val takenPhotoBitmap = _takenPhotoBitmap.asStateFlow()

    fun onPhotoTaken(bitmap: Bitmap) {
        _takenPhotoBitmap.value = bitmap
    }

    fun resetPhoto() {
        _takenPhotoBitmap.value = null
    }

    fun saveTakenPhoto() {
        _takenPhotoBitmap.value?.let { photoTaken ->
            viewModelScope.launch {
                saveCapturedPhotoUseCase(photoTaken)
            }
        }
    }
}