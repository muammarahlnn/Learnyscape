package com.muammarahlnn.learnyscape.feature.member

import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MemberContract, 25/01/2024 22.21
 */
interface MemberContract {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val lecturers: List<ClassMemberState> = listOf(),
        val students: List<ClassMemberState> = listOf(),
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }


    data class ClassMemberState(
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val name: String = "",
        val profilePicUrl: String = "",
    )

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchClassMembers : Event
    }

    sealed interface Effect
}