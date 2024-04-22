package com.muammarahlnn.learnyscape.feature.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
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
import com.muammarahlnn.learnyscape.feature.profile.ProfileContract.Effect
import com.muammarahlnn.learnyscape.feature.profile.ProfileContract.Event
import com.muammarahlnn.learnyscape.feature.profile.ProfileContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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
    private val uploadProfilePicUseCase: UploadProfilePicUseCase,
    private val getProfilePicUseCase: GetProfilePicUseCase,
    private val saveImageToFileUseCase: SaveImageToFileUseCase,
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    override fun event(event: Event) {
        when (event) {
            Event.OnGetProfilePic ->
                getProfilePic()

            Event.OnGetCapturedPhoto ->
                getCapturedPhoto()

            is Event.OnShowChangePhotoProfileBottomSheet ->
                showChangePhotoProfileBottomSheet(event.show)

            is Event.OnShowLogoutDialog ->
                showLogoutDialog(event.show)

            Event.OnLogout ->
                logout()

            is Event.OnUpdateProfilePic ->
                updateProfilePic(event.imageFile)

            Event.OnGalleryActionClick ->
                openGallery()
        }
    }

    private fun getProfilePic() {
        viewModelScope.launch {
            getProfilePicUseCase().asResult().collect { result ->
                result.onLoading {
                    updateStateOnLoading()
                }.onSuccess { profilePic ->
                    updateState {
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
                    saveImageToFileUseCase(capturedPhoto).collect { imageFile ->
                        if (imageFile != null) {
                            updateProfilePic(imageFile)
                        } else {
                            updateStateOnError("Error save image to file")
                        }
                    }
                }
            }.join()

            launch {
                resetCapturedPhotoUseCase()
            }
        }
    }

    private fun updateProfilePic(profilePicFile: File) {
        viewModelScope.launch {
            uploadProfilePicUseCase(profilePicFile).asResult().collect { result ->
                result.onLoading {
                    updateStateOnLoading()
                }.onSuccess {
                    getProfilePic()
                    showToast("Profile pic updated successfully")
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
        updateState {
            it.copy(
                showChangePhotoProfileBottomSheet = show
            )
        }
    }

    private fun showLogoutDialog(show: Boolean) {
        updateState {
            it.copy(
                showLogoutDialog = show
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            updateState {
                it.copy(
                    showLogoutDialog = false,
                    isLogout = true,
                )
            }
        }
    }

    private fun updateStateOnLoading() {
        updateState {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Loading,
            )
        }
    }

    private fun updateStateOnError(message: String) {
        Log.e(TAG, message)
        updateState {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Success(null),
            )
        }
        showToast(message)
    }

    private fun updateStateOnException(exception: Throwable?, message: String) {
        Log.e(TAG, exception?.message.toString())
        updateState {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Success(null),
            )
        }
        showToast(message)
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    private fun openGallery() {
        viewModelScope.emitEffect(Effect.OpenGallery)
        showChangePhotoProfileBottomSheet(false)
    }
    companion object {

        private const val TAG = "ProfileViewModel"
    }
}