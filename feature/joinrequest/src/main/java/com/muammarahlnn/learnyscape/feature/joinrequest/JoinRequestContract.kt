package com.muammarahlnn.learnyscape.feature.joinrequest

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.WaitingListModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestContract, 17/01/2024 22.07
 */
interface JoinRequestContract :
    BaseContract<JoinRequestContract.State, JoinRequestContract.Event>,
    EffectProvider<JoinRequestContract.Effect>,
    RefreshProvider {

    data class State(
        val classId: String = "",
        val uiState: JoinRequestUiState = JoinRequestUiState.Loading,
    )

    sealed interface Event {

        data object FetchWaitingList : Event

        data object OnCloseClick : Event

        data class OnAcceptStudent(val studentId: String) : Event

        data class OnRejectStudent(val studentId: String) : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect

        data class ShowToast(val message: String) : Effect
    }
}

sealed interface JoinRequestUiState {

    data object Loading : JoinRequestUiState

    data class Success(val waitingList: List<WaitingListModel>) : JoinRequestUiState

    data object SuccessEmpty : JoinRequestUiState

    data class Error(val message: String) : JoinRequestUiState
}