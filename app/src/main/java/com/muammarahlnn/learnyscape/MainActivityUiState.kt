package com.muammarahlnn.learnyscape

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MainActivityUiState, 21/04/2024 19.12
 */
internal sealed interface MainActivityUiState {

    data object Loading : MainActivityUiState

    data class Success(val isUserLoggedIn: Boolean) : MainActivityUiState
}