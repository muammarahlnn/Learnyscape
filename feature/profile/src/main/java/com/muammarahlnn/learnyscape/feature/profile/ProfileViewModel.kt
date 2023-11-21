package com.muammarahlnn.learnyscape.feature.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.GetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.ResetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileViewModel, 09/10/2023 21.37 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getCapturedPhotoUseCase: GetCapturedPhotoUseCase,
    private val resetCapturedPhotoUseCase: ResetCapturedPhotoUseCase,
) : ViewModel() {

    private val _newProfilePic = MutableStateFlow<Bitmap?>(null)

    val newProfilePic = _newProfilePic.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                val capturedPhoto = getCapturedPhotoUseCase().first()
                _newProfilePic.update { capturedPhoto }
            }
            launch {
                resetCapturedPhotoUseCase()
            }
        }
    }
}