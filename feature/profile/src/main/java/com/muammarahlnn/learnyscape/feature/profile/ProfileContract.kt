package com.muammarahlnn.learnyscape.feature.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileContract, 28/11/2023 20.38 by Muammar Ahlan Abimanyu
 */
interface ProfileContract :
    BaseContract<ProfileContract.State, ProfileContract.Event>,
    EffectProvider<ProfileContract.Effect> {

    data class State(
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val showChangePhotoProfileBottomSheet: MutableState<Boolean> = mutableStateOf(false),
        val showLogoutDialog: MutableState<Boolean> = mutableStateOf(false),
    )

    sealed interface Event {

        data object OnGetProfilePic : Event

        data object OnGetCapturedPhoto : Event

        data class OnShowChangePhotoProfileBottomSheet(val show: Boolean) : Event

        data class OnUpdateProfilePic(val imageFile: File) : Event

        data class OnShowLogoutDialog(val show: Boolean) : Event

        data object OnLogout : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }
}