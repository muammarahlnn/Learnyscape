package com.muammarahlnn.learnyscape.feature.pendingrequest

import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestContract, 29/02/2024 22.30
 */
interface PendingRequestContract {

    data class State(
        val uiState: UiState = UiState.Loading,
        val pendingRequestClasses: List<PendingRequestModel> = emptyList(),
        val selectedPendingRequest: PendingRequestModel? = null,
        val showCancelRequestClassDialog: Boolean = false,
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data object SuccessEmpty : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchPendingRequestClasses : Event

        data class OnSelectCancelRequestClass(val pendingRequest: PendingRequestModel) : Event

        data object OnDismissCancelRequestClass : Event

        data object OnCancelPendingRequestClass : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }
}