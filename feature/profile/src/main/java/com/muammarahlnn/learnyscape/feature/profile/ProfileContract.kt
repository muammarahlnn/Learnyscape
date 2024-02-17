package com.muammarahlnn.learnyscape.feature.profile

import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileContract, 28/11/2023 20.38 by Muammar Ahlan Abimanyu
 */
interface ProfileContract  {

    data class State(
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val showChangePhotoProfileBottomSheet: Boolean = false,
        val showLogoutDialog: Boolean = false,
    )

    sealed interface Event {

        data object OnGetProfilePic : Event

        data object OnGetCapturedPhoto : Event

        data class OnShowChangePhotoProfileBottomSheet(val show: Boolean) : Event

        data object OnGalleryActionClick : Event

        data class OnUpdateProfilePic(val imageFile: File) : Event

        data class OnShowLogoutDialog(val show: Boolean) : Event

        data object OnLogout : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect

        data object OpenGallery : Effect
    }
}