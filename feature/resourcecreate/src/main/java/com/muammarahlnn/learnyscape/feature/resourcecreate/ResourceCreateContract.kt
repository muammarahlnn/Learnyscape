package com.muammarahlnn.learnyscape.feature.resourcecreate

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateContract, 18/12/2023 05.48
 */
interface ResourceCreateContract :
    BaseContract<ResourceCreateContract.State, ResourceCreateContract.Event> {

    data class State(
        val description: String = "",
        val attachments: List<File> = listOf(),
        val showUploadAttachmentBottomSheet: Boolean = false,
    )

    sealed interface Event {

        data class OnDescriptionChange(val description: String) : Event

        data class OnShowUploadAttachmentBottomSheet(val show: Boolean) : Event

        data class OnUploadFile(val selectedFile: File) : Event
    }
}