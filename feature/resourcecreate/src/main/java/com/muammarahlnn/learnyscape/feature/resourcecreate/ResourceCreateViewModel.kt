package com.muammarahlnn.learnyscape.feature.resourcecreate

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.GetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.ResetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.file.SaveImageToFileUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcecreate.CreateAnnouncementUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcecreate.CreateAssignmentUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcecreate.CreateModuleUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcecreate.CreateQuizUseCase
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.MultipleChoiceOption
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.MultipleChoiceQuestion
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.PhotoAnswerQuestion
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.ResourceCreateArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateViewModel, 18/12/2023 05.54
 */
@HiltViewModel
class ResourceCreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createAnnouncementUseCase: CreateAnnouncementUseCase,
    private val createModuleUseCase: CreateModuleUseCase,
    private val createAssignmentUseCase: CreateAssignmentUseCase,
    private val createQuizUseCase: CreateQuizUseCase,
    private val getCapturedPhotoUseCase: GetCapturedPhotoUseCase,
    private val resetPhotoUseCase: ResetCapturedPhotoUseCase,
    private val saveImageToFileUseCase: SaveImageToFileUseCase,
) : ViewModel(), ResourceCreateContract {

    private val resourceCreateArgs = ResourceCreateArgs(savedStateHandle)

    private val _state = MutableStateFlow(
        ResourceCreateContract.State(
            classId = resourceCreateArgs.classId,
            resourceType = ClassResourceType.getClassResourceType(resourceCreateArgs.resourceTypeOrdinal)
        )
    )
    override val state: StateFlow<ResourceCreateContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ResourceCreateContract.Effect>()
    override val effect: SharedFlow<ResourceCreateContract.Effect> = _effect.asSharedFlow()

    override fun event(event: ResourceCreateContract.Event) {
        when (event) {
            ResourceCreateContract.Event.OnCloseClick ->
                navigateBack()

            ResourceCreateContract.Event.OnCreateResourceClick ->
                createResource()

            is ResourceCreateContract.Event.OnTitleChange ->
                onTitleChange(event.title)

            is ResourceCreateContract.Event.OnDescriptionChange ->
                onDescriptionChange(event.description)

            ResourceCreateContract.Event.OnAddAttachmentClick ->
                showAddAttachmentBottomSheet(true)

            ResourceCreateContract.Event.OnCameraClick ->
                navigateToCamera()

            ResourceCreateContract.Event.OnUploadFileClick ->
                openFiles()

            ResourceCreateContract.Event.OnDismissUploadAttachmentBottomSheet ->
                showAddAttachmentBottomSheet(false)

            is ResourceCreateContract.Event.OnFileSelected ->
                addAttachmentToCurrentAttachments(event.selectedFile)

            ResourceCreateContract.Event.OnGetCapturedPhoto ->
                getCapturedPhoto()

            is ResourceCreateContract.Event.OnAttachmentClick ->
                openAttachment(event.attachment)

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

            ResourceCreateContract.Event.OnDismissCreatingResourceDialog ->
                showCreatingResourceDialog(false)

            ResourceCreateContract.Event.OnConfirmSuccessCreatingResourceDialog -> {
                showCreatingResourceDialog(false)
                navigateBack()
            }
        }
    }

    private fun createResource() {
        showCreatingResourceDialog(true)

        when (_state.value.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> createAnnouncement()
            ClassResourceType.MODULE -> createModule()
            ClassResourceType.ASSIGNMENT -> createAssignment()
            ClassResourceType.QUIZ -> createQuiz()
        }
    }

    private fun createAnnouncement() {
        if (_state.value.description.isEmpty()) {
            onErrorCreatingResource("Announcement caption can't be empty")
            return
        }

        viewModelScope.launch {
            createAnnouncementUseCase(
                classId = _state.value.classId,
                description = _state.value.description,
                attachments = _state.value.attachments,
            ).asResult().collect { result ->
                result.onLoading {
                    onLoadingCreatingResource()
                }.onSuccess { message ->
                    onSuccessCreatingResource(message)
                }.onNoInternet { message ->
                    onErrorCreatingResource(message)
                }.onError { _, message ->
                    onErrorCreatingResource(message)
                }.onException { exception, message ->
                    onErrorCreatingResource(message, exception)
                }
            }
        }
    }

    private fun createModule() {
        if (_state.value.title.isEmpty()) {
            onErrorCreatingResource("Module title can't be empty")
            return
        }

        viewModelScope.launch {
            createModuleUseCase(
                classId = _state.value.classId,
                title = _state.value.title,
                description = _state.value.description,
                attachments = _state.value.attachments,
            ).asResult().collect { result ->
                result.onLoading {
                    onLoadingCreatingResource()
                }.onSuccess { message ->
                    onSuccessCreatingResource(message)
                }.onNoInternet { message ->
                    onErrorCreatingResource(message)
                }.onError { _, message ->
                    onErrorCreatingResource(message)
                }.onException { exception, message ->
                    onErrorCreatingResource(message, exception)
                }
            }
        }
    }

    private fun createAssignment() {
        if (_state.value.title.isEmpty()) {
            onErrorCreatingResource("Assignment title can't be empty")
            return
        }

        if (_state.value.dueDate == null) {
            onErrorCreatingResource("Due date can't be empty")
            return
        }

        viewModelScope.launch {
            createAssignmentUseCase(
                classId = _state.value.classId,
                title = _state.value.title,
                description = _state.value.description,
                dueDate = _state.value.dueDate!!,
                attachments = _state.value.attachments,
            ).asResult().collect { result ->
                result.onLoading {
                    onLoadingCreatingResource()
                }.onSuccess { message ->
                    onSuccessCreatingResource(message)
                }.onNoInternet { message ->
                    onErrorCreatingResource(message)
                }.onError { _, message ->
                    onErrorCreatingResource(message)
                }.onException { exception, message ->
                    onErrorCreatingResource(message, exception)
                }
            }
        }
    }

    private fun createQuiz() {
        if (state.value.title.isEmpty()) {
            onErrorCreatingResource("Quiz title can't be empty")
            return
        }

        when (state.value.quizType) {
            QuizType.NONE -> {
                onErrorCreatingResource("Please select quiz type first")
                return
            }

            QuizType.MULTIPLE_CHOICE -> {
                if (state.value.multipleChoiceQuestions.isEmpty()) {
                    onErrorCreatingResource("Multiple choice questions can't be empty")
                    return
                }
            }

            QuizType.PHOTO_ANSWER -> {
                if (state.value.photoAnswerQuestions.isEmpty()) {
                    onErrorCreatingResource("Photo answer questions can't be empty")
                    return
                }
            }
        }

        val startDate = state.value.startDate
        val endDate = state.value.endDate
        if (startDate == null) {
            onErrorCreatingResource("Start date can't be empty")
            return
        }
        if (endDate == null) {
            onErrorCreatingResource("End date can't be empty")
            return
        }
        if (endDate.isBefore(startDate)) {
            onErrorCreatingResource("End date can't be before start date")
            return
        }

        if (state.value.duration == 0) {
            onErrorCreatingResource("Duration can't be empty")
            return
        }

        val questions = when (state.value.quizType) {
            QuizType.MULTIPLE_CHOICE -> {
                state.value.multipleChoiceQuestions.map {
                    MultipleChoiceQuestionModel(
                        question = it.question.value,
                        options = MultipleChoiceQuestionModel.Options(
                            optionA = it.options[MultipleChoiceOption.A]?.value,
                            optionB = it.options[MultipleChoiceOption.B]?.value,
                            optionC = it.options[MultipleChoiceOption.C]?.value,
                            optionD = it.options[MultipleChoiceOption.D]?.value,
                            optionE = it.options[MultipleChoiceOption.E]?.value,
                        )
                    )
                }
            }

            QuizType.PHOTO_ANSWER -> {
                state.value.photoAnswerQuestions.map {
                    MultipleChoiceQuestionModel(
                        question = it.question.value,
                    )
                }
            }

            QuizType.NONE -> return
        }

        viewModelScope.launch {
            createQuizUseCase(
                classId = state.value.classId,
                title = state.value.title,
                description = state.value.description,
                quizType = state.value.quizType.name,
                questions = questions,
                startDate = startDate,
                endDate = endDate,
                duration = state.value.duration,
            )
                .asResult()
                .collect { result ->
                    result.onLoading {
                        onLoadingCreatingResource()
                    }.onSuccess {
                        onSuccessCreatingResource("Quiz successfully created")
                    }.onNoInternet { message ->
                        onErrorCreatingResource(message)
                    }.onError { _, message ->
                        onErrorCreatingResource(message)
                    }.onException { exception, message ->
                        onErrorCreatingResource(message, exception)
                    }
                }
        }
    }

    private fun onLoadingCreatingResource() {
        _state.update {
            it.copy(
                creatingResourceDialogState = CreatingResourceDialogState.Loading
            )
        }
    }

    private fun onSuccessCreatingResource(message: String) {
        _state.update {
            it.copy(
                creatingResourceDialogState = CreatingResourceDialogState.Success(
                    message.lowercase().replaceFirstChar { char -> char.uppercase() }
                )
            )
        }
    }

    private fun onErrorCreatingResource(message: String, exception: Throwable? = null) {
        exception?.let {
            Log.e(TAG, it.message.toString())
        }

        _state.update {
            it.copy(
                creatingResourceDialogState = CreatingResourceDialogState.Error(message)
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.ShowToast(message))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.NavigateBack)
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

    private fun navigateToCamera() {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.NavigateToCamera)
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

    private fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                getCapturedPhotoUseCase().first()?.let { capturedPhoto ->
                    saveImageToFileUseCase(capturedPhoto).collect { imageFile ->
                        if (imageFile != null) {
                            addAttachmentToCurrentAttachments(imageFile)
                        } else {
                            showToast("Error capturing photo")
                        }
                    }
                }
            }.join()

            launch {
                resetPhotoUseCase()
            }
        }
    }

    private fun openAttachment(attachment: File) {
        viewModelScope.launch {
            _effect.emit(ResourceCreateContract.Effect.OpenAttachment(attachment))
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
        val currentDueDate = LocalDateTime.of(dueDate, dueTime)
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

    private fun showCreatingResourceDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    creatingResourceDialog = show
                )
            )
        }
    }

    companion object {

        private const val TAG = "ResourceCreateViewModel"
    }
}