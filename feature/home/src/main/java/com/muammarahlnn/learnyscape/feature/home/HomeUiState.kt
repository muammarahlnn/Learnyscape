package com.muammarahlnn.learnyscape.feature.home

import com.muammarahlnn.learnyscape.core.model.data.ClassInfoModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeUiState, 12/10/2023 22.42 by Muammar Ahlan Abimanyu
 */
sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(val classes: List<ClassInfoModel>) : HomeUiState

    data object SuccessEmptyClasses : HomeUiState

    data class Error(val message: String) : HomeUiState
}
