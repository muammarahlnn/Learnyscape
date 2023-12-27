package com.muammarahlnn.learnyscape.feature.resourcecreate

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import java.io.File
import java.time.LocalDate
import java.time.LocalTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateContract, 18/12/2023 05.48
 */
interface ResourceCreateContract :
    BaseContract<ResourceCreateContract.State, ResourceCreateContract.Event>,
    EffectProvider<ResourceCreateContract.Effect> {

    data class State(
        val resourceType: ClassResourceType = ClassResourceType.ANNOUNCEMENT,
        val title: String = "",
        val description: String = "",
        val attachments: List<File> = listOf(),
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility(),
        val selectedAttachmentIndex: Int = -1,
        val dueDate: LocalDate? = null,
        val dueTime: LocalTime? = null,
    )

    data class OverlayComposableVisibility(
        val addAttachmentBottomSheet: Boolean = false,
        val removeAttachmentBottomSheet: Boolean = false,
        val setDueDateDialog: Boolean = false,
        val dueDatePickerDialog: Boolean = false,
        val dueTimePickerDialog: Boolean = false,
    )

    sealed interface Event {

        data object OnCloseClick : Event

        data class OnTitleChange(val title: String) : Event

        data class OnDescriptionChange(val description: String) : Event

        data object OnAddAttachmentClick : Event

        data object OnUploadFileClick : Event

        data object OnCameraClick : Event

        data object OnDismissUploadAttachmentBottomSheet : Event

        data class OnFileSelected(val selectedFile: File) : Event

        data class OnMoreVertAttachmentClick(val attachmentIndex: Int) : Event

        data object OnDismissRemoveAttachmentBottomSheet : Event

        data object OnRemoveAttachment : Event

        data object OnDueDateClick : Event

        data object OnConfirmSetDueDate : Event

        data object OnDismissSetDueDateDialog : Event

        data object OnSetDateClick: Event

        data class OnConfirmPickDate(val date: LocalDate) : Event

        data object OnDismissDueDatePickerDialog : Event

        data object OnSetTimeClick : Event

        data class OnConfirmPickTime(val time: LocalTime) : Event

        data object OnDismissDueTimePickerDialog : Event
    }

    sealed interface Effect {

        data object CloseScreen : Effect

        data object OpenFiles : Effect

        data object OpenCamera : Effect
    }
}