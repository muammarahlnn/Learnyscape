package com.muammarahlnn.learnyscape.feature.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.GetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.ResetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.file.SaveImageToFileUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.LogoutUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.UploadProfilePicUseCase
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
    private val saveImageToFileUseCase: SaveImageToFileUseCase,
    private val uploadProfilePicUseCase: UploadProfilePicUseCase,
) : ViewModel() {

    private val _newProfilePic = MutableStateFlow<Bitmap?>(null)
    val newProfilePic = _newProfilePic.asStateFlow()

    private val _profilePicUiState = MutableStateFlow<ProfilePicUiState>(ProfilePicUiState.None)
    val profilePicUiState = _profilePicUiState.asStateFlow()

    fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                val capturedPhoto = getCapturedPhotoUseCase().first()
                _newProfilePic.update { capturedPhoto }
            }.join()

            launch {
                resetCapturedPhotoUseCase()
            }
        }
    }

    fun uploadImage(image: Bitmap) {
        viewModelScope.launch {
            saveImageToFileUseCase(image).collect { imageFile ->
                if (imageFile != null) {
                    uploadProfilePicUseCase(imageFile).asResult().collect { result ->
                        result.onLoading {
                            _profilePicUiState.update {
                                ProfilePicUiState.Loading
                            }
                        }.onSuccess {
                            _profilePicUiState.update {
                                ProfilePicUiState.SuccessUploadProfilePic
                            }
                        }.onError { _, message ->
                            Log.e(TAG, message)
                            _profilePicUiState.update {
                                ProfilePicUiState.ErrorUploadProfilePic(message)
                            }
                        }.onException { exception, message ->
                            Log.e(TAG, exception?.message.toString())
                            _profilePicUiState.update {
                                ProfilePicUiState.ErrorUploadProfilePic(message)
                            }
                        }
                    }
                } else {
                    _profilePicUiState.update {
                        Log.e(TAG, "Error save image to file")
                        ProfilePicUiState.ErrorUploadProfilePic("Error save image to file")
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    companion object {

        private const val TAG = "ProfileViewModel"
    }
}