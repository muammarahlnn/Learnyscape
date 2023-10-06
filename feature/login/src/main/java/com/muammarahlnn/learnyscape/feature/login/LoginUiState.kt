package com.muammarahlnn.learnyscape.feature.login

import com.muammarahlnn.learnyscape.core.model.data.LoginModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginUiState, 03/10/2023 18.53 by Muammar Ahlan Abimanyu
 */
sealed interface LoginUiState {

    data class Success(val loginModel: LoginModel) : LoginUiState

    data class Error(val message: String) : LoginUiState

    object Loading : LoginUiState

    object None : LoginUiState
}