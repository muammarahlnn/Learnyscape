package com.muammarahlnn.learnyscape.feature.assignment

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.AssignmentOverviewModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentContract, 14/01/2024 14.54
 */
interface AssignmentContract :
    BaseContract<AssignmentContract.State, AssignmentContract.Event>,
    EffectProvider<AssignmentContract.Effect>,
    RefreshProvider {

    data class State(
        val classId: String = "",
        val uiState: AssignmentUiState = AssignmentUiState.Loading
    )

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchAssignments : Event

        data object OnNavigateBack : Event

        data class OnNavigateToResourceDetails(val assignmentId: String) : Event

        data object OnNavigateToResourceCreate : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect

        data class NavigateToResourceDetails(
            val resourceId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect

        data class NavigateToResourceCreate(
            val classId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect
    }
}

sealed interface AssignmentUiState {

    data object Loading : AssignmentUiState

    data class Success(val assignments: List<AssignmentOverviewModel>) : AssignmentUiState

    data object SuccessEmpty : AssignmentUiState

    data class Error(val message: String) : AssignmentUiState
}