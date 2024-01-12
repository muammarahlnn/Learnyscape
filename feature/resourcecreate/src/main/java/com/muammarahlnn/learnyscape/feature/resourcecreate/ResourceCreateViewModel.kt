package com.muammarahlnn.learnyscape.feature.resourcecreate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.MultipleChoiceQuestion
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.PhotoAnswerQuestion
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
                onConfirmSetDueDateDialog(event.dueDate, event.dueTime)

            ResourceCreateContract.Event.OnDismissSetDueDateDialog ->
                showSetDueDateDialog(false)

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

            ResourceCreateContract.Event.OnShowQuestionsScreen ->
                showQuestionsScreen()

            ResourceCreateContract.Event.OnCloseQuestionsScreen ->
                closeQuestionsScreen()

            is ResourceCreateContract.Event.OnSaveQuestions ->
                onSaveQuestions(event.multipleChoiceQuestions, event.photoAnswerQuestions)

            is ResourceCreateContract.Event.OnUnfilledQuestions ->
                showToast(event.message)
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.ShowToast(message))
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

    private fun onConfirmSetDueDateDialog(
        dueDate: LocalDate?,
        dueTime: LocalTime?,
    ) {
        val currentDueDate = DueDate(
            date = dueDate,
            time = dueTime,
        )
        _state.update {
            when (it.dueDateType) {
                DueDateType.DUE_DATE -> it.copy(dueDate = currentDueDate)
                DueDateType.START_DATE -> it.copy(startDate = currentDueDate)
                DueDateType.END_DATE -> it.copy(endDate = currentDueDate)
            }
        }

        showSetDueDateDialog(false)
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

    private fun showQuestionsScreen() {
        if (_state.value.quizType == QuizType.NONE) {
            showToast("Please select quiz type first")
        } else {
            _state.update {
                it.copy(
                    showQuestionsScreen = true
                )
            }
        }
    }

    private fun closeQuestionsScreen() {
        _state.update {
            it.copy(
                showQuestionsScreen = false
            )
        }
    }

    private fun onSaveQuestions(
        multipleChoiceQuestions: List<MultipleChoiceQuestion>,
        photoAnswerQuestions: List<PhotoAnswerQuestion>,
    ) {
        _state.update {
            it.copy(
                multipleChoiceQuestions = multipleChoiceQuestions,
                photoAnswerQuestions = photoAnswerQuestions,
            )
        }
        closeQuestionsScreen()
    }
}