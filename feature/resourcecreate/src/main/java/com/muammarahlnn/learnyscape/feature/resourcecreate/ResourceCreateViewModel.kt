package com.muammarahlnn.learnyscape.feature.resourcecreate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateViewModel, 18/12/2023 05.54
 */
class ResourceCreateViewModel : ViewModel(), ResourceCreateContract {

    private val _state = MutableStateFlow(ResourceCreateContract.State())
    override val state: StateFlow<ResourceCreateContract.State> = _state

    override fun event(event: ResourceCreateContract.Event) {
        when (event) {
            is ResourceCreateContract.Event.OnDescriptionChange ->
                onDescriptionChange(event.description)
            is ResourceCreateContract.Event.OnShowUploadAttachmentBottomSheet  ->
                showUploadAttachmentBottomSheet(event.show)
            is ResourceCreateContract.Event.OnUploadFile ->
                onUploadFile(event.selectedFile)
        }
    }

    private fun onDescriptionChange(description: String) {
        _state.update {
            it.copy(description = description)
        }
    }

    private fun showUploadAttachmentBottomSheet(show: Boolean) {
        _state.update {
            it.copy(showUploadAttachmentBottomSheet = show)
        }
    }

    private fun onUploadFile(file: File) {
        _state.update {
            val addedAttachments = it.attachments.toMutableList().apply {
                add(file)
            }
            it.copy(
                attachments = addedAttachments
            )
        }
    }
}