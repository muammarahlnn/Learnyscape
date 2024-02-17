package com.muammarahlnn.learnyscape.feature.joinrequest

import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestContract, 17/01/2024 22.07
 */
interface JoinRequestContract {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val waitingListStudents: List<WaitingListStudentState> = listOf(),
    )


    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data object SuccessEmpty : UiState

        data class Error(val message: String) : UiState
    }

    data class WaitingListStudentState(
        val id: String = "",
        val userId: String = "",
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val name: String = "",
    )

    sealed interface Event {

        data object FetchWaitingList : Event

        data class OnAcceptStudent(val studentId: String) : Event

        data class OnRejectStudent(val studentId: String) : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }
}