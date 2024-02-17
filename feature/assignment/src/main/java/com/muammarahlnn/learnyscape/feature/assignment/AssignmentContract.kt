package com.muammarahlnn.learnyscape.feature.assignment

import com.muammarahlnn.learnyscape.core.model.data.AssignmentOverviewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentContract, 14/01/2024 14.54
 */
interface AssignmentContract {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val assignmentOrdinal: Int = ClassResourceType.ASSIGNMENT.ordinal,
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val assignments: List<AssignmentOverviewModel>) : UiState

        data object SuccessEmpty : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchAssignments : Event
    }

    sealed interface Effect
}