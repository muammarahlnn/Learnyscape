package com.muammarahlnn.learnyscape.feature.search

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SearchContract, 13/12/2023 02.59
 */
interface SearchContract :
    BaseContract<SearchContract.State, SearchContract.Event>,
    EffectProvider<SearchContract.Effect>,
    RefreshProvider {

    data class State(
        val uiState: UiState = UiState.Loading,
        val searchQuery: String = "",
        val selectedAvailableClass: AvailableClassModel? = null,
        val showJoinRequestDialog: Boolean = false,
        val joinRequestClassDialogLoading: Boolean = false,
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val availableClasses: List<AvailableClassModel>) : UiState

        data object SuccessEmpty : UiState

        data class NoInternet(val message: String) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchAvailableClasses : Event

        data class OnSearchQueryChanged(val query: String) : Event

        data class OnAvailableClassClick(val availableClass: AvailableClassModel) : Event

        data object OnRequestJoinClass : Event

        data object OnDismissJoinClass : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }
}