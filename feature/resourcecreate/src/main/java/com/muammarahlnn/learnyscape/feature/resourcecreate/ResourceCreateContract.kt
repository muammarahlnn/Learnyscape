package com.muammarahlnn.learnyscape.feature.resourcecreate

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateContract, 18/12/2023 05.48
 */
interface ResourceCreateContract :
    BaseContract<ResourceCreateContract.State, ResourceCreateContract.Event>,
    EffectProvider<ResourceCreateContract.Effect> {

    data class State(
        val description: String = "",
        val attachments: List<File> = listOf(),
        val showUploadAttachmentBottomSheet: Boolean = false,
    )

    sealed interface Event {

        data object OnCloseClick : Event

        data class OnDescriptionChange(val description: String) : Event

        data object OnAddAttachmentClick : Event

        data object OnUploadFileClick : Event

        data object OnCameraClick : Event

        data object OnDismissUploadAttachmentBottomSheet : Event

        data class OnFileSelected(val selectedFile: File) : Event
    }

    sealed interface Effect {

        data object CloseScreen : Effect

        data object OpenFiles : Effect

        data object OpenCamera : Effect
    }
}