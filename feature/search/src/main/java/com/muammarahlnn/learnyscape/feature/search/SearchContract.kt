package com.muammarahlnn.learnyscape.feature.search

import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SearchContract, 13/12/2023 02.59
 */
interface SearchContract {

    data class State(
        val uiState: UiState = UiState.Loading,
        val searchUiState: SearchUiState = SearchUiState.Success(emptyList()),
        val searchQuery: String = "",
        val selectedAvailableClass: AvailableClassModel? = null,
        val showJoinRequestDialog: Boolean = false,
        val joinRequestClassDialogLoading: Boolean = false,
        val showCancelRequestDialog: Boolean = false,
        val cancelRequestClassDialogLoading: Boolean = false,
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data object SuccessEmpty : UiState

        data class NoInternet(val message: String) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface SearchUiState {

        data object Loading : SearchUiState

        data class Success(val availableClasses: List<AvailableClassModel>) : SearchUiState

        data object SuccessEmpty : SearchUiState

        data class Error(val message: String) : SearchUiState
    }

    sealed interface Event {

        data object FetchAvailableClasses : Event

        data class OnSearchQueryChanged(val query: String) : Event

        data class OnAvailableClassClick(val availableClass: AvailableClassModel) : Event

        data object OnRequestJoinClass : Event

        data object OnDismissJoinClass : Event

        data object OnCancelRequestClass : Event

        data object OnDismissCancelRequestClass : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }

    sealed interface Navigation {

        data object NavigateToPendingClass : Navigation
    }
}