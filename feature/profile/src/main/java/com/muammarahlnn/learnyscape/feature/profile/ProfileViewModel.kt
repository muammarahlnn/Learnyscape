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
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
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
) : ViewModel(), ProfileContract {

    private val _state = MutableStateFlow(ProfileContract.State())
    override val state: StateFlow<ProfileContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileContract.Effect>()
    override val effect: SharedFlow<ProfileContract.Effect> = _effect.asSharedFlow()

    override fun event(event: ProfileContract.Event) = when (event) {
        ProfileContract.Event.OnGetProfilePic ->
            getProfilePic()

        ProfileContract.Event.OnGetCapturedPhoto ->
            getCapturedPhoto()

        is ProfileContract.Event.OnShowChangePhotoProfileBottomSheet ->
            showChangePhotoProfileBottomSheet(event.show)

        is ProfileContract.Event.OnShowLogoutDialog ->
            showLogoutDialog(event.show)

        ProfileContract.Event.OnLogout ->
            logout()

        is ProfileContract.Event.OnUploadGalleryImage ->
            uploadGalleryPhoto(event.imageFile)
    }

    private fun getProfilePic() {
        viewModelScope.launch {
            getProfilePicUseCase().asResult().collect { result ->
                result.onLoading {
                    updateStateOnLoading()
                }.onSuccess { profilePic ->
                    _state.update {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic),
                        )
                    }
                }.onNoInternet { message ->
                    updateStateOnError(message)
                }.onError { _, message ->
                    updateStateOnError(message)
                }.onException { exception, message ->
                    updateStateOnException(exception, message)
                }
            }
        }
    }

    private fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                getCapturedPhotoUseCase().first()?.let { capturedPhoto ->
                    uploadCapturePhoto(capturedPhoto)
                }
            }.join()

            launch {
                resetCapturedPhotoUseCase()
            }
        }
    }

    private fun uploadCapturePhoto(profilePic: Bitmap) {
        viewModelScope.launch {
            saveImageToFileUseCase(profilePic).collect { profilePicFile ->
                if (profilePicFile != null) {
                    invokeUploadProfilePicUseCase(profilePicFile)
                } else {
                    updateStateOnError("Error save image to file")
                }
            }
        }
    }

    private fun uploadGalleryPhoto(imageFile: File) {
        invokeUploadProfilePicUseCase(imageFile)
    }

    private fun invokeUploadProfilePicUseCase(profilePicFile: File) {
        viewModelScope.launch {
            uploadProfilePicUseCase(profilePicFile).asResult().collect { result ->
                result.onLoading {
                    updateStateOnLoading()
                }.onSuccess {
                    getProfilePic()
                }.onNoInternet { message ->
                    updateStateOnError(message)
                }.onError { _, message ->
                    updateStateOnError(message)
                }.onException { exception, message ->
                    updateStateOnException(exception, message)
                }
            }
        }
    }

    private fun showChangePhotoProfileBottomSheet(show: Boolean) {
        _state.update {
            it.apply {
                showChangePhotoProfileBottomSheet.value = show
            }
        }
    }

    private fun showLogoutDialog(show: Boolean) {
        _state.update {
            it.apply {
                showLogoutDialog.value = show
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    private fun updateStateOnLoading() {
        _state.update {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Loading,
            )
        }
    }

    private fun updateStateOnError(message: String) {
        Log.e(TAG, message)

        _state.update {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Success(null),
            )
        }

        viewModelScope.launch {
            _effect.emit(
                ProfileContract.Effect.ShowToast(message)
            )
        }
    }

    private fun updateStateOnException(exception: Throwable?, message: String) {
        Log.e(TAG, exception?.message.toString())

        _state.update {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Success(null),
            )
        }

        viewModelScope.launch {
            _effect.emit(
                ProfileContract.Effect.ShowToast(message)
            )
        }
    }

    companion object {

        private const val TAG = "ProfileViewModel"
    }
}