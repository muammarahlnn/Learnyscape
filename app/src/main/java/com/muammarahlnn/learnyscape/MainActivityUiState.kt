package com.muammarahlnn.learnyscape


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MainActivityUiState, 08/10/2023 21.30 by Muammar Ahlan Abimanyu
 */
sealed interface MainActivityUiState {

    data object Loading : MainActivityUiState

    data class Success(val isLoggedIn: Boolean) : MainActivityUiState
}