package com.muammarahlnn.learnyscape.feature.search.uistate


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file JoinRequestClassDialogUiState, 20/11/2023 18.21 by Muammar Ahlan Abimanyu
 */
sealed interface JoinRequestClassDialogUiState {

    data object None : JoinRequestClassDialogUiState

    data object Loading : JoinRequestClassDialogUiState

    data object Success : JoinRequestClassDialogUiState

    data class NoInternet(val message: String) : JoinRequestClassDialogUiState

    data class Error(val message: String) : JoinRequestClassDialogUiState
}