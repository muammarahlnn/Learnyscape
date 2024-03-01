package com.muammarahlnn.learnyscape.feature.pendingrequest

import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestContract, 29/02/2024 22.30
 */
interface PendingRequestContract {

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val pendingRequestClasses: List<PendingRequestModel>) : UiState

        data object SuccessEmpty : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchPendingRequestClasses : Event
    }

    sealed interface Effect
}