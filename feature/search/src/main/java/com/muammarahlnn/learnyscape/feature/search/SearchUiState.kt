package com.muammarahlnn.learnyscape.feature.search

import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchUiState, 16/11/2023 20.54 by Muammar Ahlan Abimanyu
 */
sealed interface SearchUiState {

    data object Loading : SearchUiState

    data class Success(
        val availableClasses: List<AvailableClassModel>
    ) : SearchUiState

    data object SuccessEmpty : SearchUiState

    data class NoInternet(val message: String) : SearchUiState

    data class Error(val message: String) : SearchUiState
}