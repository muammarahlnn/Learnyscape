package com.muammarahlnn.learnyscape.feature.home

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File HomeContract, 12/12/2023 20.21
 */
interface HomeContract :
    BaseContract<HomeContract.State, HomeContract.Event>,
    RefreshProvider {

    data class State(
        val searchQuery: String = "",
        val uiState: UiState = UiState.Loading,
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val classes: List<EnrolledClassInfoModel>) : UiState

        data object SuccessEmptyClasses : UiState

        data class NoInternet(val message: String) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchEnrolledClasses : Event

        data class OnSearchQueryChanged(val query: String) : Event
    }
}