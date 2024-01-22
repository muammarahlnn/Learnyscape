package com.muammarahlnn.learnyscape.feature.resourcedetails

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
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
        val name: String = "",
        val date: String = "",
        val description: String = "",
        val attachments: List<File> = listOf(),
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility()
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }

    data class OverlayComposableVisibility(
        val showAddWorkBottomSheet: Boolean = false,
        val showStartQuizDialog: Boolean = false,
    )

    sealed interface Event {

        data object FetchResourceDetails : Event

        data object OnBackClick : Event

        data object OnAddWorkButtonClick : Event

        data object OnCameraActionClick : Event

        data class OnAttachmentClick(val attachment: File) : Event

        data object OnDismissAddWorkBottomSheet : Event

        data object OnStartQuizButtonClick : Event

        data class OnConfirmStartQuizDialog(
            val quizTypeOrdinal: Int,
            val quizName: String,
            val quizDuration: Int,
        ) : Event

        data object OnDismissStartQuizDialog : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect

        data object NavigateToCamera : Effect

        data class OpenAttachment(val attachment: File) : Effect

        data class NavigateToQuizSession(
            val quizTypeOrdinal: Int,
            val quizName: String,
            val quizDuration: Int,
        ) : Effect
    }
}