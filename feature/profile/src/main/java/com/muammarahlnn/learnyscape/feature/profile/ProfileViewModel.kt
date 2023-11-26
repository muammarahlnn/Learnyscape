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
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicUseCase
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
    private val getProfilePicUseCase: GetProfilePicUseCase
) : ViewModel() {

    private val _profilePicUiState = MutableStateFlow<ProfilePicUiState>(ProfilePicUiState.Loading)
    val profilePicUiState = _profilePicUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getProfilePic()
        }
    }

    private suspend fun getProfilePic() {
        getProfilePicUseCase().asResult().collect { result ->
            result.onLoading {
                onResultLoading()
            }.onSuccess { profilePic ->
                _profilePicUiState.update {
                    ProfilePicUiState.SuccessGetProfilePic(profilePic)
                }
            }.onError { _, message ->
                onResultError(message)
            }.onException { exception, message ->
                onResultException(exception, message)
            }
        }
    }

    fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                 getCapturedPhotoUseCase().first()?.let {
                     uploadImage(it)
                 }
            }.join()

            launch {
                resetCapturedPhotoUseCase()
            }
        }
    }

    private suspend fun uploadImage(image: Bitmap) {
        saveImageToFileUseCase(image).collect { imageFile ->
            if (imageFile != null) {
                uploadProfilePicUseCase(imageFile).asResult().collect { result ->
                    result.onLoading {
                        onResultLoading()
                    }.onSuccess {
                        getProfilePic()
                    }.onError { _, message ->
                        onResultError(message)
                    }.onException { exception, message ->
                        onResultException(exception, message)
                    }
                }
            } else {
                onResultError("Error save image to file")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    private fun onResultLoading() {
        _profilePicUiState.update {
            ProfilePicUiState.Loading
        }
    }

    private fun onResultError(message: String) {
        Log.e(TAG, message)
        _profilePicUiState.update {
            ProfilePicUiState.ErrorUploadProfilePic(message)
        }
    }

    private fun onResultException(exception: Throwable?, message: String) {
        Log.e(TAG, exception?.message.toString())
        _profilePicUiState.update {
            ProfilePicUiState.ErrorUploadProfilePic(message)
        }
    }

    companion object {

        private const val TAG = "ProfileViewModel"
    }
}