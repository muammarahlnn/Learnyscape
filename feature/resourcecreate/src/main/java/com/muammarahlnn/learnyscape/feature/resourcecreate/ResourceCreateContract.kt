package com.muammarahlnn.learnyscape.feature.resourcecreate

import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.MultipleChoiceQuestion
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.PhotoAnswerQuestion
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateContract, 18/12/2023 05.48
 */
interface ResourceCreateContract  {

    data class State(
        val classId: String = "",
        val resourceId: String = "",
        val resourceType: ClassResourceType = ClassResourceType.ANNOUNCEMENT,
        val isEdit: Boolean = false,
        val isLoading: Boolean = false,
        val title: String = "",
        val description: String = "",
        val attachments: List<File> = listOf(),
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility(),
        val selectedAttachmentIndex: Int = -1,
        val dueDateType: DueDateType = DueDateType.DUE_DATE,
        val dueDate: LocalDateTime? = null,
        val startDate: LocalDateTime? = null,
        val endDate: LocalDateTime? = null,
        val quizType: QuizType = QuizType.NONE,
        val duration: Int = 0,
        val showQuestionsScreen: Boolean = false,
        val multipleChoiceQuestions: List<MultipleChoiceQuestion> = listOf(),
        val photoAnswerQuestions: List<PhotoAnswerQuestion> = listOf(),
        val creatingResourceDialogState: CreatingResourceDialogUiState = CreatingResourceDialogUiState.Loading,
    )


    sealed interface CreatingResourceDialogUiState {

        data object Loading : CreatingResourceDialogUiState

        data class Success(val message: String) : CreatingResourceDialogUiState

        data class Error(val message: String) : CreatingResourceDialogUiState
    }

    data class OverlayComposableVisibility(
        val addAttachmentBottomSheet: Boolean = false,
        val removeAttachmentBottomSheet: Boolean = false,
        val setDueDateDialog: Boolean = false,
        val quizTypeBottomSheet: Boolean = false,
        val durationDialog: Boolean = false,
        val creatingResourceDialog: Boolean = false,
    )


    sealed interface Event {

        data object FetchResourceDetails : Event

        data object OnCreateResourceClick : Event

        data object OnEditResourceClick : Event

        data class OnTitleChange(val title: String) : Event

        data class OnDescriptionChange(val description: String) : Event

        data class OnShowAddAttachmentBottomSheet(val show: Boolean) : Event

        data object OnUploadFileClick : Event

        data class OnFileSelected(val selectedFile: File) : Event

        data object OnGetCapturedPhoto : Event

        data class OnAttachmentClick(val attachment: File) : Event

        data class OnMoreVertAttachmentClick(val attachmentIndex: Int) : Event

        data object OnDismissRemoveAttachmentBottomSheet : Event

        data object OnRemoveAttachment : Event

        data class OnDueDateClick(val dueDateType: DueDateType) : Event

        data class OnConfirmSetDueDate(
            val dueDate: LocalDate?,
            val dueTime: LocalTime?,
        ) : Event

        data object OnDismissSetDueDateDialog : Event

        data object OnQuizTypeClick : Event

        data object OnDismissQuizTypeBottomSheet : Event

        data class OnSelectQuizTypeBottomSheetOption(val quizType: QuizType) : Event

        data object OnDurationClick : Event

        data object OnDismissDurationDialog: Event

        data class OnConfirmSetDurationDialog(val duration: Int) : Event

        data object OnShowQuestionsScreen : Event

        data object OnCloseQuestionsScreen : Event

        data class OnSaveQuestions(
            val multipleChoiceQuestions: List<MultipleChoiceQuestion>,
            val photoAnswerQuestions: List<PhotoAnswerQuestion>,
        ) : Event

        data class OnUnfilledQuestions(val message: String) : Event

        data object OnDismissCreatingResourceDialog : Event

        data object OnConfirmSuccessCreatingResourceDialog : Event
    }

    sealed interface Effect {

        data object OpenFiles : Effect

        data class OpenAttachment(val attachment: File) : Effect

        data class ShowToast(val message: String) : Effect
    }
}