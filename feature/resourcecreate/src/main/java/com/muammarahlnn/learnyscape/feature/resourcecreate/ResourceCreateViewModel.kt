package com.muammarahlnn.learnyscape.feature.resourcecreate

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.result.Result
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
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAnnouncementDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAssignmentDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizDetailsUseCase
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract.CreatingResourceDialogUiState
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract.Effect
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract.Event
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract.State
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.MultipleChoiceOption
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.MultipleChoiceQuestion
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.PhotoAnswerQuestion
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.ResourceCreateArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
    private val getAnnouncementDetailsUseCase: GetAnnouncementDetailsUseCase,
    private val getModuleDetailsUseCase: GetModuleDetailsUseCase,
    private val getAssignmentDetailsUseCase: GetAssignmentDetailsUseCase,
    private val getQuizDetailsUseCase: GetQuizDetailsUseCase,
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    private val resourceCreateArgs = ResourceCreateArgs(savedStateHandle)

    init {
        updateState {
            it.copy(
                classId = resourceCreateArgs.classId,
                resourceId = resourceCreateArgs.resourceId.orEmpty(),
                resourceType = ClassResourceType.getClassResourceType(resourceCreateArgs.resourceTypeOrdinal),
                isEdit = !resourceCreateArgs.resourceId.isNullOrEmpty(),
            )
        }
    }

    override fun event(event: Event) {
        when (event) {
            Event.FetchResourceDetails ->
                fetchResourceDetails()

            Event.OnCreateResourceClick ->
                createResource()

            is Event.OnTitleChange ->
                onTitleChange(event.title)

            is Event.OnDescriptionChange ->
                onDescriptionChange(event.description)

            is Event.OnShowAddAttachmentBottomSheet ->
                showAddAttachmentBottomSheet(event.show)

            Event.OnUploadFileClick ->
                openFiles()

            is Event.OnFileSelected ->
                addAttachmentToCurrentAttachments(event.selectedFile)

            Event.OnGetCapturedPhoto ->
                getCapturedPhoto()

            is Event.OnAttachmentClick ->
                openAttachment(event.attachment)

            is Event.OnMoreVertAttachmentClick ->
                onMoreVertAttachmentClick(event.attachmentIndex)

            Event.OnDismissRemoveAttachmentBottomSheet ->
                dismissRemoveAttachmentBottomSheet()

            is Event.OnRemoveAttachment ->
                removeAttachmentFromCurrentAttachments()

            is Event.OnDueDateClick -> {
                showSetDueDateDialog(true)
                updateDueDateType(event.dueDateType)
            }

            is Event.OnConfirmSetDueDate ->
                onConfirmSetDueDateDialog(event.dueDate, event.dueTime)

            Event.OnDismissSetDueDateDialog ->
                showSetDueDateDialog(false)

            Event.OnQuizTypeClick ->
                showQuizTypeBottomSheet(true)

            Event.OnDismissQuizTypeBottomSheet ->
                showQuizTypeBottomSheet(false)

            is Event.OnSelectQuizTypeBottomSheetOption ->
                onSelectQuizTypeBottomSheetOption(event.quizType)

            Event.OnDurationClick ->
                showDurationDialog(true)

            Event.OnDismissDurationDialog ->
                showDurationDialog(false)

            is Event.OnConfirmSetDurationDialog ->
                onConfirmSetDuration(event.duration)

            Event.OnShowQuestionsScreen ->
                showQuestionsScreen()

            Event.OnCloseQuestionsScreen ->
                closeQuestionsScreen()

            is Event.OnSaveQuestions ->
                onSaveQuestions(event.multipleChoiceQuestions, event.photoAnswerQuestions)

            is Event.OnUnfilledQuestions ->
                showToast(event.message)

            Event.OnDismissCreatingResourceDialog ->
                showCreatingResourceDialog(false)

            Event.OnConfirmSuccessCreatingResourceDialog -> {
                showCreatingResourceDialog(false)
            }
        }
    }

    private fun fetchResourceDetails() {
        when (state.value.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> fetchAnnouncementDetails()
            ClassResourceType.MODULE -> fetchModuleDetails()
            ClassResourceType.ASSIGNMENT -> fetchAssignmentDetails()
            ClassResourceType.QUIZ -> fetchQuizDetails()
        }
    }

    private fun fetchAnnouncementDetails() {
        viewModelScope.launch {
            getAnnouncementDetailsUseCase(announcementId = state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) { announcementDetails ->
                        updateState {
                            it.copy(
                                description = announcementDetails.description,
                                attachments = announcementDetails.attachments,
                            )
                        }
                    }
                }
        }
    }

    private fun fetchModuleDetails() {
        viewModelScope.launch {
            getModuleDetailsUseCase(moduleId = state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) { moduleDetails ->
                        updateState {
                            it.copy(
                                title = moduleDetails.name,
                                description = moduleDetails.description,
                                attachments = moduleDetails.attachments,
                            )
                        }
                    }
                }
        }
    }

    private fun fetchAssignmentDetails() {
        viewModelScope.launch {
            getAssignmentDetailsUseCase(assignmentId = state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) { assignmentDetails ->
                        updateState {
                            it.copy(
                                title = assignmentDetails.name,
                                description = assignmentDetails.description,
                                attachments = assignmentDetails.attachments,
                                dueDate = assignmentDetails.dueDate.toLocalDateTime(),
                            )
                        }
                    }
                }
        }
    }

    private fun fetchQuizDetails() {
        viewModelScope.launch {
            getQuizDetailsUseCase(quizId = state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) { quizDetails ->
                        // TODO: also update the questions
                        updateState {
                            it.copy(
                                title = quizDetails.name,
                                description = quizDetails.description,
                                quizType = quizDetails.quizType,
                                startDate = quizDetails.startDate.toLocalDateTime(),
                                endDate = quizDetails.endDate.toLocalDateTime(),
                                duration = quizDetails.duration,
                            )
                        }
                    }
                }
        }
    }

    private fun String.toLocalDateTime(): LocalDateTime =
        LocalDateTime.parse(
            this,
            DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm")
        )

    private inline fun <reified T> handleFetchResourceDetails(
        result: Result<T>,
        onSuccess: (T) -> Unit,
    ) {
        result.onLoading {
            updateLoading(true)
        }.onSuccess { data ->
            onSuccess(data)
            updateLoading(false)
        }.onNoInternet { message ->
            showToast(message)
            updateLoading(false)
        }.onError { _, message ->
            showToast(message)
            updateLoading(false)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            showToast(message)
            updateLoading(false)
        }
    }

    private fun updateLoading(loading: Boolean) {
        updateState {
            it.copy(isLoading = loading)
        }
    }

    private fun createResource() {
        showCreatingResourceDialog(true)

        when (state.value.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> createAnnouncement()
            ClassResourceType.MODULE -> createModule()
            ClassResourceType.ASSIGNMENT -> createAssignment()
            ClassResourceType.QUIZ -> createQuiz()
        }
    }

    private fun createAnnouncement() {
        if (state.value.description.isEmpty()) {
            onErrorCreatingResource("Announcement caption can't be empty")
            return
        }

        viewModelScope.launch {
            createAnnouncementUseCase(
                classId = state.value.classId,
                description = state.value.description,
                attachments = state.value.attachments,
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
        if (state.value.title.isEmpty()) {
            onErrorCreatingResource("Module title can't be empty")
            return
        }

        viewModelScope.launch {
            createModuleUseCase(
                classId = state.value.classId,
                title = state.value.title,
                description = state.value.description,
                attachments = state.value.attachments,
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
        if (state.value.title.isEmpty()) {
            onErrorCreatingResource("Assignment title can't be empty")
            return
        }

        if (state.value.dueDate == null) {
            onErrorCreatingResource("Due date can't be empty")
            return
        }

        viewModelScope.launch {
            createAssignmentUseCase(
                classId = state.value.classId,
                title = state.value.title,
                description = state.value.description,
                dueDate = state.value.dueDate!!,
                attachments = state.value.attachments,
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
        updateState {
            it.copy(
                creatingResourceDialogState = CreatingResourceDialogUiState.Loading
            )
        }
    }

    private fun onSuccessCreatingResource(message: String) {
        updateState {
            it.copy(
                creatingResourceDialogState = CreatingResourceDialogUiState.Success(
                    message.lowercase().replaceFirstChar { char -> char.uppercase() }
                )
            )
        }
    }

    private fun onErrorCreatingResource(message: String, exception: Throwable? = null) {
        exception?.let {
            Log.e(TAG, it.message.toString())
        }

        updateState {
            it.copy(
                creatingResourceDialogState = CreatingResourceDialogUiState.Error(message)
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    private fun onTitleChange(title: String) {
        updateState {
            it.copy(title = title)
        }
    }

    private fun onDescriptionChange(description: String) {
        updateState {
            it.copy(description = description)
        }
    }

    private fun showAddAttachmentBottomSheet(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    addAttachmentBottomSheet = show
                )
            )
        }
    }

    private fun openFiles() {
        viewModelScope.emitEffect(Effect.OpenFiles)
        showAddAttachmentBottomSheet(false)
    }

    private fun addAttachmentToCurrentAttachments(file: File) {
        updateState {
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
        viewModelScope.emitEffect(Effect.OpenAttachment(attachment))
    }

    private fun onMoreVertAttachmentClick(attachmentIndex: Int) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    removeAttachmentBottomSheet = true
                ),
                selectedAttachmentIndex = attachmentIndex,
            )
        }
    }

    private fun dismissRemoveAttachmentBottomSheet() {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    removeAttachmentBottomSheet = false
                ),
                selectedAttachmentIndex = -1,
            )
        }
    }

    private fun removeAttachmentFromCurrentAttachments() {
        updateState {
            it.copy(
                attachments = it.attachments.toMutableList().apply {
                    removeAt(it.selectedAttachmentIndex)
                }.toList()
            )
        }
        dismissRemoveAttachmentBottomSheet()
    }

    private fun showSetDueDateDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    setDueDateDialog = show
                )
            )
        }
    }

    private fun updateDueDateType(dueDateType: DueDateType) {
        updateState {
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
        updateState {
            when (it.dueDateType) {
                DueDateType.DUE_DATE -> it.copy(dueDate = currentDueDate)
                DueDateType.START_DATE -> it.copy(startDate = currentDueDate)
                DueDateType.END_DATE -> it.copy(endDate = currentDueDate)
            }
        }

        showSetDueDateDialog(false)
    }

    private fun showQuizTypeBottomSheet(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    quizTypeBottomSheet = show
                )
            )
        }
    }

    private fun onSelectQuizTypeBottomSheetOption(quizType: QuizType) {
        updateState {
            it.copy(
                quizType = quizType
            )
        }
        showQuizTypeBottomSheet(false)
    }

    private fun showDurationDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    durationDialog = show
                )
            )
        }
    }

    private fun onConfirmSetDuration(duration: Int) {
        updateState {
            it.copy(
                duration = duration
            )
        }
        showDurationDialog(false)
    }

    private fun showQuestionsScreen() {
        if (state.value.quizType == QuizType.NONE) {
            showToast("Please select quiz type first")
        } else {
            updateState {
                it.copy(
                    showQuestionsScreen = true
                )
            }
        }
    }

    private fun closeQuestionsScreen() {
        updateState {
            it.copy(
                showQuestionsScreen = false
            )
        }
    }

    private fun onSaveQuestions(
        multipleChoiceQuestions: List<MultipleChoiceQuestion>,
        photoAnswerQuestions: List<PhotoAnswerQuestion>,
    ) {
        updateState {
            it.copy(
                multipleChoiceQuestions = multipleChoiceQuestions,
                photoAnswerQuestions = photoAnswerQuestions,
            )
        }
        closeQuestionsScreen()
    }

    private fun showCreatingResourceDialog(show: Boolean) {
        updateState {
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