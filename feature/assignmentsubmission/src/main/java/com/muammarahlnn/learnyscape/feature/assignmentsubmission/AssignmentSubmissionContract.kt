package com.muammarahlnn.learnyscape.feature.assignmentsubmission

import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionContract, 19/02/2024 18.47
 */
interface AssignmentSubmissionContract {

    data class State(
        val uiState: UiState = UiState.Loading,
        val assignmentId: String = "",
        val submission: AssignmentSubmissionModel = AssignmentSubmissionModel(),
        val isSaveStudentCurrentWorkLoading: Boolean = false,
        val isStudentCurrentWorkChange: Boolean = false,
        val isTurnInSubmissionLoading: Boolean = false,
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility(),
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }

    data class OverlayComposableVisibility(
        val showAddWorkBottomSheet: Boolean = false,
        val showTurnInDialog: Boolean = false,
        val showSaveYourWorkInfoDialog: Boolean = false,
        val showUnsubmitDialog: Boolean = false,
    )

    sealed interface Event {

        data class SetAssignmentId(val assignmentId: String) : Event

        data object FetchStudentSubmission : Event

        data class OnShowTurnInDialog(val show: Boolean) : Event

        data class OnShowSaveYourWorkInfoDialog(val show: Boolean) : Event

        data class OnShowUnsubmitDialog(val show: Boolean) : Event

        data class OnShowAddWorkBottomSheet(val show: Boolean) : Event

        data object OnUploadFileActionClick : Event

        data class OnFileSelected(val file: File) : Event

        data object OnGetCapturedPhoto : Event

        data class OnAttachmentClick(val attachment: File) : Event

        data class OnRemoveAssignment(val index: Int) : Event

        data object OnSaveStudentCurrentWorkClick : Event

        data object OnTurnInSubmission : Event

        data object OnUnsubmitSubmission : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect

        data object OpenFiles : Effect

        data class OpenAttachment(val attachment: File) : Effect
    }
}