package com.muammarahlnn.learnyscape.feature.profile


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfilePicUiState, 26/11/2023 17.19 by Muammar Ahlan Abimanyu
 */
sealed interface ProfilePicUiState {

    data object None : ProfilePicUiState

    data object Loading : ProfilePicUiState

    data object SuccessUploadProfilePic : ProfilePicUiState

    data class ErrorUploadProfilePic(val message: String) : ProfilePicUiState
}