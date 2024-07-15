package com.muammarahlnn.learnyscape.feature.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsContract, 19/01/2024 11.51
 */
interface ResourceDetailsContract {

    data class State(
        val classId: String = "",
        val resourceId: String = "",
        val resourceType: ClassResourceType = ClassResourceType.ANNOUNCEMENT,
        val uiState: UiState = UiState.Loading,
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility(),
        val deletingResourceUiState: UiState = UiState.Loading,
        val name: String = "",
        val date: String = "",
        val description: String = "",
        val attachments: List<File> = listOf(),
        val startQuizDate: String = "",
        val endQuizDate: String = "",
        val quizDuration: Int = 0,
        val quizType: QuizType = QuizType.NONE,
        val isQuizTaken: Boolean = false,
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data object NotFound : UiState

        data class Error(val message: String) : UiState
    }

    data class OverlayComposableVisibility(
        val showDeleteResourceDialog: Boolean = false,
        val showDeletingResourceDialog: Boolean = false,
        val showAddWorkBottomSheet: Boolean = false,
        val showStartQuizDialog: Boolean = false,
    )

    sealed interface Event {

        data object FetchResourceDetails : Event

        data object OnDeleteClick : Event

        data object OnConfirmDeleteResourceDialog : Event

        data object OnDismissDeleteResourceDialog : Event

        data object OnConfirmSuccessDeletingResourceDialog : Event

        data object OnDismissDeletingResourceDialog : Event

        data class OnAttachmentClick(val attachment: File) : Event

        data object OnStartQuizButtonClick : Event

        data object OnDismissStartQuizDialog : Event
    }

    sealed interface Effect {

        data class OpenAttachment(val attachment: File) : Effect
    }
}