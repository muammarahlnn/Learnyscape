package com.muammarahlnn.learnyscape.feature.resourcecreate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.ResourceCreateArgs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.LocalTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateViewModel, 18/12/2023 05.54
 */
class ResourceCreateViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ResourceCreateContract {

    private val resourceCreateArgs = ResourceCreateArgs(savedStateHandle)

    private val _state = MutableStateFlow(
        ResourceCreateContract.State(
            resourceType = ClassResourceType.getClassResourceType(resourceCreateArgs.resourceTypeOrdinal)
        )
    )
    override val state: StateFlow<ResourceCreateContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ResourceCreateContract.Effect>()
    override val effect: SharedFlow<ResourceCreateContract.Effect> = _effect.asSharedFlow()

    override fun event(event: ResourceCreateContract.Event) {
        when (event) {
            ResourceCreateContract.Event.OnCloseClick ->
                closeScreen()

            is ResourceCreateContract.Event.OnTitleChange ->
                onTitleChange(event.title)

            is ResourceCreateContract.Event.OnDescriptionChange ->
                onDescriptionChange(event.description)

            ResourceCreateContract.Event.OnAddAttachmentClick ->
                showAddAttachmentBottomSheet(true)

            ResourceCreateContract.Event.OnCameraClick ->
                openCamera()

            ResourceCreateContract.Event.OnUploadFileClick ->
                openFiles()

            ResourceCreateContract.Event.OnDismissUploadAttachmentBottomSheet ->
                showAddAttachmentBottomSheet(false)

            is ResourceCreateContract.Event.OnFileSelected ->
                addAttachmentToCurrentAttachments(event.selectedFile)

            is ResourceCreateContract.Event.OnMoreVertAttachmentClick ->
                onMoreVertAttachmentClick(event.attachmentIndex)

            ResourceCreateContract.Event.OnDismissRemoveAttachmentBottomSheet ->
                dismissRemoveAttachmentBottomSheet()

            is ResourceCreateContract.Event.OnRemoveAttachment ->
                removeAttachmentFromCurrentAttachments()

            is ResourceCreateContract.Event.OnDueDateClick -> {
                showSetDueDateDialog(true)
                updateDueDateType(event.dueDateType)
            }

            is ResourceCreateContract.Event.OnConfirmSetDueDate ->
                onConfirmSetDueDateDialog()

            ResourceCreateContract.Event.OnDismissSetDueDateDialog ->
                dismissSetDueDateDialog()

            ResourceCreateContract.Event.OnSetDateClick ->
                showDueDatePickerDialog(true)

            is ResourceCreateContract.Event.OnConfirmPickDate ->
                onConfirmPickDate(event.date)

            ResourceCreateContract.Event.OnDismissDueDatePickerDialog ->
                showDueDatePickerDialog(false)

            ResourceCreateContract.Event.OnSetTimeClick ->
                showDueTimePickerDialog(true)

            is ResourceCreateContract.Event.OnConfirmPickTime ->
                onConfirmPickTime(event.time)

            ResourceCreateContract.Event.OnDismissDueTimePickerDialog ->
                showDueTimePickerDialog(false)

            ResourceCreateContract.Event.OnQuizTypeClick ->
                showQuizTypeBottomSheet(true)

            ResourceCreateContract.Event.OnDismissQuizTypeBottomSheet ->
                showQuizTypeBottomSheet(false)

            is ResourceCreateContract.Event.OnSelectQuizTypeBottomSheetOption ->
                onSelectQuizTypeBottomSheetOption(event.quizType)

            ResourceCreateContract.Event.OnDurationClick ->
                showDurationDialog(true)

            ResourceCreateContract.Event.OnDismissDurationDialog ->
                showDurationDialog(false)

            is ResourceCreateContract.Event.OnConfirmSetDurationDialog ->
                onConfirmSetDuration(event.duration)
        }
    }

    private fun closeScreen() {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.CloseScreen)
        }
    }

    private fun onTitleChange(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    private fun onDescriptionChange(description: String) {
        _state.update {
            it.copy(description = description)
        }
    }

    private fun showAddAttachmentBottomSheet(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    addAttachmentBottomSheet = show
                )
            )
        }
    }

    private fun openCamera() {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.OpenCamera)
        }
        showAddAttachmentBottomSheet(false)
    }

    private fun openFiles() {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.OpenFiles)
        }
        showAddAttachmentBottomSheet(false)
    }

    private fun addAttachmentToCurrentAttachments(file: File) {
        _state.update {
            val addedAttachments = it.attachments.toMutableList().apply {
                add(file)
            }
            it.copy(
                attachments = addedAttachments
            )
        }
    }

    private fun onMoreVertAttachmentClick(attachmentIndex: Int) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    removeAttachmentBottomSheet = true
                ),
                selectedAttachmentIndex = attachmentIndex,
            )
        }
    }

    private fun dismissRemoveAttachmentBottomSheet() {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    removeAttachmentBottomSheet = false
                ),
                selectedAttachmentIndex = -1,
            )
        }
    }

    private fun removeAttachmentFromCurrentAttachments() {
        _state.update {
            it.copy(
                attachments = it.attachments.toMutableList().apply {
                    removeAt(it.selectedAttachmentIndex)
                }.toList()
            )
        }
        dismissRemoveAttachmentBottomSheet()
    }

    private fun showSetDueDateDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    setDueDateDialog = show
                )
            )
        }
    }

    private fun updateDueDateType(dueDateType: DueDateType) {
        _state.update {
            it.copy(
                dueDateType = dueDateType,
            )
        }
    }

    private fun onConfirmSetDueDateDialog() {
        val currentDate = LocalDate.now()
        val currentTime = LocalTime.now()
        _state.update {
            when (it.dueDateType) {
                DueDateType.DUE_DATE -> it.copy(
                    dueDate = it.dueDate.copy(
                        date = it.dueDate.date ?: currentDate,
                        time = it.dueDate.time ?: currentTime,
                    ),
                )
                DueDateType.START_DATE -> it.copy(
                    startDate = it.startDate.copy(
                        date = it.startDate.date ?: currentDate,
                        time = it.startDate.time ?: currentTime,
                    ),
                )
                DueDateType.END_DATE -> it.copy(
                    endDate = it.endDate.copy(
                        date = it.endDate.date ?: currentDate,
                        time = it.endDate.time ?: currentTime,
                    ),
                )
            }
        }
        showSetDueDateDialog(false)
    }

    private fun dismissSetDueDateDialog() {
        _state.update {
            when (it.dueDateType) {
                DueDateType.DUE_DATE -> it.copy(
                    dueDate = DueDate(),
                )
                DueDateType.START_DATE -> it.copy(
                    startDate = DueDate(),
                )
                DueDateType.END_DATE -> it.copy(
                    endDate = DueDate(),
                )
            }
        }
        showSetDueDateDialog(false)
    }

    private fun showDueDatePickerDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    dueDatePickerDialog = show
                )
            )
        }
    }

    private fun onConfirmPickDate(date: LocalDate) {
        _state.update {
            when (it.dueDateType) {
                DueDateType.DUE_DATE -> it.copy(
                    dueDate = it.dueDate.copy(
                        date = date,
                    )
                )
                DueDateType.START_DATE -> it.copy(
                    startDate = it.startDate.copy(
                        date = date,
                    )
                )
                DueDateType.END_DATE -> it.copy(
                    endDate = it.endDate.copy(
                        date = date,
                    )
                )
            }
        }

        showDueDatePickerDialog(false)
    }

    private fun showDueTimePickerDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    dueTimePickerDialog = show
                )
            )
        }
    }

    private fun onConfirmPickTime(time: LocalTime) {
        _state.update {
            when (it.dueDateType) {
                DueDateType.DUE_DATE -> it.copy(
                    dueDate = it.dueDate.copy(
                        time = time,
                    )
                )
                DueDateType.START_DATE -> it.copy(
                    startDate = it.startDate.copy(
                        time = time,
                    )
                )
                DueDateType.END_DATE -> it.copy(
                    endDate = it.endDate.copy(
                        time = time,
                    )
                )
            }
        }
        showDueTimePickerDialog(false)
    }

    private fun showQuizTypeBottomSheet(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    quizTypeBottomSheet = show
                )
            )
        }
    }

    private fun onSelectQuizTypeBottomSheetOption(quizType: QuizType) {
        _state.update {
            it.copy(
                quizType = quizType
            )
        }
        showQuizTypeBottomSheet(false)
    }

    private fun showDurationDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    durationDialog = show
                )
            )
        }
    }

    private fun onConfirmSetDuration(duration: Int) {
        _state.update {
            it.copy(
                duration = duration
            )
        }
        showDurationDialog(false)
    }
}