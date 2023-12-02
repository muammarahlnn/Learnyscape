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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
) : ViewModel(), ProfileContract {

    private val _state = MutableStateFlow(ProfileContract.State(loading = true))
    override val state: StateFlow<ProfileContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileContract.Effect>()
    override val effect: SharedFlow<ProfileContract.Effect> = _effect.asSharedFlow()

    override fun event(event: ProfileContract.Event) = when (event) {
        ProfileContract.Event.OnGetProfilePic -> getProfilePic()
        ProfileContract.Event.OnGetCapturedPhoto -> getCapturedPhoto()
        is ProfileContract.Event.OnShowChangePhotoProfileBottomSheet ->
            onShowChangePhotoProfileBottomSheet(event.show)
        is ProfileContract.Event.OnShowLogoutDialog -> onShowLogoutDialog(event.show)
        ProfileContract.Event.OnLogout -> logout()
        is ProfileContract.Event.ShowToast -> showToast(event.message)
    }

    private fun getProfilePic() {
        viewModelScope.launch {
            getProfilePicUseCase().asResult().collect { result ->
                result.onLoading {
                    updateStateOnLoading()
                }.onSuccess { profilePic ->
                    _state.update {
                        it.copy(
                            loading = false,
                            profilePic = profilePic
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
                    uploadProfilePic(capturedPhoto)
                }
            }.join()

            launch {
                resetCapturedPhotoUseCase()
            }
        }
    }

    private suspend fun uploadProfilePic(profilePic: Bitmap) {
        saveImageToFileUseCase(profilePic).collect { profilePicFile ->
            if (profilePicFile != null) {
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
            } else {
                updateStateOnError("Error save image to file")
            }
        }
    }

    private fun onShowChangePhotoProfileBottomSheet(show: Boolean) {
        _state.update {
            it.apply {
                showChangePhotoProfileBottomSheet.value = show
            }
        }
    }

    private fun onShowLogoutDialog(show: Boolean) {
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

    private fun showToast(message: String) {
        viewModelScope.launch {
            _effect.emit(
                ProfileContract.Effect.ShowToast(message)
            )
        }
    }

    private fun updateStateOnLoading() {
        _state.update {
            it.copy(
                loading = true,
            )
        }
    }

    private fun updateStateOnError(message: String) {
        Log.e(TAG, message)

        _state.update {
            it.copy(
                loading = false,
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
                loading = false,
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