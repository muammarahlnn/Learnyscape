package com.muammarahlnn.learnyscape.feature.profile

import android.graphics.Bitmap


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfilePicUiState, 26/11/2023 17.19 by Muammar Ahlan Abimanyu
 */
sealed interface ProfilePicUiState {

    data object Loading : ProfilePicUiState

    data class SuccessGetProfilePic(val profilePic: Bitmap?) : ProfilePicUiState

    data class ErrorUploadProfilePic(val message: String) : ProfilePicUiState
}