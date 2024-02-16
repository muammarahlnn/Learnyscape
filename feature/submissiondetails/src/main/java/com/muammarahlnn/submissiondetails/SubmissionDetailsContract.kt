package com.muammarahlnn.submissiondetails

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsContract, 13/02/2024 23.45
 */
interface SubmissionDetailsContract :
    BaseContract<SubmissionDetailsContract.State, SubmissionDetailsContract.Event>,
    EffectProvider<SubmissionDetailsContract.Effect>
{

    data class State(
        val submissionType: SubmissionType = SubmissionType.ASSIGNMENT,
        val submissionId: String = "",
        val studentId: String = "",
        val studentName: String = "",
        val uiState: UiState = UiState.Loading,
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val assignmentSubmission: AssignmentSubmissionModel = AssignmentSubmissionModel(),
        val quizAnswers: List<StudentQuizAnswerModel> = listOf(),
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchSubmissionDetails : Event

        data class OnAttachmentClick(val attachment: File) : Event

        data object OnBackClick : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect

        data class OpenAttachment(val attachment: File) : Effect
    }
}