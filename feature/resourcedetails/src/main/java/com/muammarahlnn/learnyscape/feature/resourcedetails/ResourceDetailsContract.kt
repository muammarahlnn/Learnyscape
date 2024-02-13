package com.muammarahlnn.learnyscape.feature.resourcedetails

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsContract, 19/01/2024 11.51
 */
interface ResourceDetailsContract :
    BaseContract<ResourceDetailsContract.State, ResourceDetailsContract.Event>,
    EffectProvider<ResourceDetailsContract.Effect>,
    RefreshProvider
{

    data class State(
        val resourceId: String = "",
        val resourceType: ClassResourceType = ClassResourceType.ANNOUNCEMENT,
        val uiState: UiState = UiState.Loading,
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility(),
        val studentWorkUiState: UiState = UiState.Loading,
        val deletingResourceUiState: UiState = UiState.Loading,
        val studentAssignmentBottomSheetUiState: UiState = UiState.Loading,
        val name: String = "",
        val date: String = "",
        val description: String = "",
        val attachments: List<File> = listOf(),
        val startQuizDate: String = "",
        val endQuizDate: String = "",
        val quizDuration: Int = 0,
        val quizType: QuizType = QuizType.NONE,
        val submittedSubmissions: List<StudentSubmissionState> = listOf(),
        val missingSubmissions: List<StudentSubmissionState> = listOf(),
        val assignmentSubmission: AssignmentSubmissionModel = AssignmentSubmissionModel(),
        val isSaveStudentCurrentWorkLoading: Boolean = false,
        val isStudentCurrentWorkChange: Boolean = false,
        val isTurnInAssignmentSubmissionLoading: Boolean = false,
        val isQuizTaken: Boolean = false,
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }

    data class StudentSubmissionState(
        val id: String = "",
        val userId: String = "",
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val name: String = "",
    )

    data class OverlayComposableVisibility(
        val showDeleteResourceDialog: Boolean = false,
        val showDeletingResourceDialog: Boolean = false,
        val showAddWorkBottomSheet: Boolean = false,
        val showStartQuizDialog: Boolean = false,
    )

    sealed interface Event {

        data object FetchResourceDetails : Event

        data object FetchStudentWorks : Event

        data object FetchStudentAssignmentSubmission : Event

        data object OnBackClick : Event

        data object OnDeleteClick : Event

        data object OnConfirmDeleteResourceDialog : Event

        data object OnDismissDeleteResourceDialog : Event

        data object OnConfirmSuccessDeletingResourceDialog : Event

        data object OnDismissDeletingResourceDialog : Event

        data object OnAddWorkButtonClick : Event

        data object OnCameraActionClick : Event

        data object OnUploadFileActionClick : Event

        data class OnFileSelected(val file: File) : Event

        data object OnGetCapturedPhoto : Event

        data class OnAttachmentClick(val attachment: File) : Event

        data object OnDismissAddWorkBottomSheet : Event

        data object OnStartQuizButtonClick : Event

        data object OnConfirmStartQuizDialog : Event

        data object OnDismissStartQuizDialog : Event

        data object OnSubmissionClick : Event

        data class OnRemoveAssignmentSubmissionAttachment(val index: Int) : Event

        data object OnSaveStudentCurrentWorkClick : Event

        data object OnTurnInAssignmentSubmission : Event

        data object OnUnsubmitAssignmentSubmission : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect

        data object NavigateBack : Effect

        data object NavigateToCamera : Effect

        data object OpenFiles : Effect

        data class OpenAttachment(val attachment: File) : Effect

        data class NavigateToQuizSession(
            val quizId: String,
            val quizTypeOrdinal: Int,
            val quizName: String,
            val quizDuration: Int,
        ) : Effect

        data class NavigateToSubmissionDetails(
            val submissionTypeOrdinal: Int,
        ) : Effect
    }
}