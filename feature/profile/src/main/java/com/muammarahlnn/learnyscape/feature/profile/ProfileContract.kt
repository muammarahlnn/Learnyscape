package com.muammarahlnn.learnyscape.feature.profile

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileContract, 28/11/2023 20.38 by Muammar Ahlan Abimanyu
 */
interface ProfileContract :
    BaseContract<ProfileContract.State, ProfileContract.Event>,
    EffectProvider<ProfileContract.Effect> {

    data class State(
        val profilePic: Bitmap? = null,
        val loading: Boolean = false,
        val showChangePhotoProfileBottomSheet: MutableState<Boolean> = mutableStateOf(false),
        val showLogoutDialog: MutableState<Boolean> = mutableStateOf(false),
    )

    sealed interface Event {

        data object OnGetProfilePic : Event

        data object OnGetCapturedPhoto : Event

        data class OnShowChangePhotoProfileBottomSheet(val show: Boolean) : Event

        data object OnCameraActionClick : Event

        data object OnGalleryActionClick : Event

        data class OnUploadGalleryImage(val imageFile: File) : Event

        data class OnShowLogoutDialog(val show: Boolean) : Event

        data object OnLogout : Event
    }

    sealed interface Effect {

        data object OpenCamera : Effect

        data object OpenGallery : Effect

        data class ShowToast(val message: String) : Effect
    }
}