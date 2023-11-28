package com.muammarahlnn.learnyscape.feature.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
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

    private val _profilePicState = MutableStateFlow(ProfilePicState(isLoading = true))
    val profilePicState = _profilePicState.asStateFlow()

    init {
        viewModelScope.launch {
            getProfilePic()
        }
    }

    private suspend fun getProfilePic() {
        getProfilePicUseCase().asResult().collect { result ->
            result.onLoading {
                onProfilePicStateLoading()
            }.onSuccess { profilePic ->
                onProfilePicStateSuccess(profilePic)
            }.onNoInternet { message ->
                onProfilePicStateError(message)
            }.onError { _, message ->
                onProfilePicStateError(message)
            }.onException { exception, message ->
                onProfilePicStateException(exception, message)
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
                        onProfilePicStateLoading()
                    }.onSuccess {
                        getProfilePic()
                    }.onNoInternet { message ->
                        onProfilePicStateError(message)
                    }.onError { _, message ->
                        onProfilePicStateError(message)
                    }.onException { exception, message ->
                        onProfilePicStateException(exception, message)
                    }
                }
            } else {
                onProfilePicStateError("Error save image to file")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    private fun onProfilePicStateLoading() {
        _profilePicState.update {
            it.copy(
                isLoading = true,
                errorMessage = "",
            )
        }
    }

    private fun onProfilePicStateSuccess(bitmap: Bitmap?) {
        _profilePicState.update {
            it.copy(
                profilePic = bitmap,
                isLoading = false,
                errorMessage = ""
            )
        }
    }

    private fun onProfilePicStateError(message: String) {
        Log.e(TAG, message)
        _profilePicState.update {
            it.copy(
                errorMessage = message,
                isLoading = false,
            )
        }
    }

    private fun onProfilePicStateException(
        exception: Throwable?,
        message: String
    ) {
        Log.e(TAG, exception?.message.toString())
        _profilePicState.update {
            it.copy(
                errorMessage = message,
                isLoading = false,
            )
        }
    }

    companion object {

        private const val TAG = "ProfileViewModel"
    }
}