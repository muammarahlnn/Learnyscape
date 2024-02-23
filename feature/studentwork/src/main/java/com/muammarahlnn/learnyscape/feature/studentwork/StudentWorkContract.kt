package com.muammarahlnn.learnyscape.feature.studentwork

import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentWorkContract, 22/02/2024 21.41
 */
interface StudentWorkContract {

    data class State(
        val resourceId: String = "",
        val studentWorkType: StudentWorkType = StudentWorkType.ASSIGNMENT,
        val uiState: UiState = UiState.Loading,
        val submittedSubmissions: List<StudentSubmissionState> = emptyList(),
        val missingSubmissions: List<StudentSubmissionState> = emptyList(),
    )

    data class StudentSubmissionState(
        val id: String = "",
        val userId: String = "",
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val name: String = "",
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchStudentWorks : Event

        data class SetStudentWorkType(val studentWorkType: StudentWorkType) : Event

        data class SetResourceId(val resourceId: String) : Event
    }

    sealed interface Effect
}