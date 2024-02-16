package com.muammarahlnn.learnyscape.feature.resourcedetails

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByIdUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteAnnouncementUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteAssignmentUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteModuleUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAnnouncementDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAssignmentDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.IsQuizTakenUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.LecturerGetAssignmentSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.StudentGetAssignmentSubmissionUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.TurnInAssignmentSubmissionUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.UpdateAssignmentAttachmentsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.UploadAssignmentAttachmentsUseCase
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.ResourceDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsViewModel, 21/08/2023 21.10 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class ResourceDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAnnouncementDetailsUseCase: GetAnnouncementDetailsUseCase,
    private val deleteAnnouncementUseCase: DeleteAnnouncementUseCase,
    private val getModuleDetailsUseCase: GetModuleDetailsUseCase,
    private val deleteModuleUseCase: DeleteModuleUseCase,
    private val getAssignmentDetailsUseCase: GetAssignmentDetailsUseCase,
    private val deleteAssignmentUseCase: DeleteAssignmentUseCase,
    private val getQuizDetailsUseCase: GetQuizDetailsUseCase,
    private val lecturerGetAssignmentSubmissionsUseCase: LecturerGetAssignmentSubmissionsUseCase,
    private val getProfilePicByIdUseCase: GetProfilePicByIdUseCase,
    private val getQuizSubmissionsUseCase: GetQuizSubmissionsUseCase,
    private val getCapturedPhotoUseCase: GetCapturedPhotoUseCase,
    private val resetPhotoUseCase: ResetCapturedPhotoUseCase,
    private val saveImageToFileUseCase: SaveImageToFileUseCase,
    private val uploadAssignmentAttachmentsUseCase: UploadAssignmentAttachmentsUseCase,
    private val studentGetAssignmentSubmissionUseCase: StudentGetAssignmentSubmissionUseCase,
    private val updateAssignmentAttachmentsUseCase: UpdateAssignmentAttachmentsUseCase,
    private val turnInAssignmentSubmissionUseCase: TurnInAssignmentSubmissionUseCase,
    private val isQuizTakenUseCase: IsQuizTakenUseCase,
) : ViewModel(), ResourceDetailsContract {

    private val resourceDetailsArgs = ResourceDetailsArgs(savedStateHandle)

    private val _state = MutableStateFlow(ResourceDetailsContract.State(
        resourceId = resourceDetailsArgs.resourceId,
        resourceType = ClassResourceType.getClassResourceType(resourceDetailsArgs.resourceTypeOrdinal)
    ))
    override val state: StateFlow<ResourceDetailsContract.State> = _state

    private val _effect = MutableSharedFlow<ResourceDetailsContract.Effect>()
    override val effect: SharedFlow<ResourceDetailsContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    override fun event(event: ResourceDetailsContract.Event) {
        when (event) {
            ResourceDetailsContract.Event.FetchResourceDetails ->
                fetchResourceDetails()

            ResourceDetailsContract.Event.FetchStudentWorks ->
                fetchStudentWorks()

            ResourceDetailsContract.Event.FetchStudentAssignmentSubmission ->
                fetchStudentAssignmentSubmission()

            ResourceDetailsContract.Event.OnBackClick ->
                navigateBack()

            ResourceDetailsContract.Event.OnDeleteClick ->
                showDeleteResourceDialog(true)

            ResourceDetailsContract.Event.OnConfirmDeleteResourceDialog ->
                deleteResource()

            ResourceDetailsContract.Event.OnDismissDeleteResourceDialog ->
                showDeleteResourceDialog(false)

            ResourceDetailsContract.Event.OnConfirmSuccessDeletingResourceDialog -> {
                showDeletingResourceDialog(false)
                navigateBack()
            }

            ResourceDetailsContract.Event.OnDismissDeletingResourceDialog ->
                showDeletingResourceDialog(false)

            ResourceDetailsContract.Event.OnAddWorkButtonClick ->
                showAddWorkBottomSheet(true)

            ResourceDetailsContract.Event.OnCameraActionClick ->
                navigateToCamera()

            ResourceDetailsContract.Event.OnUploadFileActionClick ->
                openFiles()

            is ResourceDetailsContract.Event.OnFileSelected ->
                addAssignmentSubmissionAttachment(event.file)

            ResourceDetailsContract.Event.OnGetCapturedPhoto ->
                getCapturedPhoto()

            is ResourceDetailsContract.Event.OnAttachmentClick ->
                openAttachment(event.attachment)

            ResourceDetailsContract.Event.OnDismissAddWorkBottomSheet ->
                showAddWorkBottomSheet(false)

            ResourceDetailsContract.Event.OnStartQuizButtonClick ->
                showStartQuizDialog(true)

            is ResourceDetailsContract.Event.OnConfirmStartQuizDialog ->
                navigateToQuizSession()

            ResourceDetailsContract.Event.OnDismissStartQuizDialog ->
                showStartQuizDialog(false)

            is ResourceDetailsContract.Event.OnSubmissionClick ->
                navigateToSubmissionDetails(event.submissionId, event.studentId, event.studentName)

            is ResourceDetailsContract.Event.OnRemoveAssignmentSubmissionAttachment ->
                removeAssignmentSubmissionAttachment(event.index)

            ResourceDetailsContract.Event.OnSaveStudentCurrentWorkClick ->
                uploadAssignmentAttachments()

            ResourceDetailsContract.Event.OnTurnInAssignmentSubmission ->
                turnInAssignmentSubmission(true)

            ResourceDetailsContract.Event.OnUnsubmitAssignmentSubmission ->
                turnInAssignmentSubmission(false)
        }
    }

    private fun fetchResourceDetails() {
        when (state.value.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> fetchAnnouncementDetails()

            ClassResourceType.MODULE -> fetchModuleDetails()

            ClassResourceType.ASSIGNMENT -> {
                fetchAssignmentDetails()
                fetchStudentWorks()
                fetchStudentAssignmentSubmission()
            }

            ClassResourceType.QUIZ -> {
                fetchQuizDetails()
                fetchStudentWorks()
            }
        }
    }

    private fun fetchAnnouncementDetails() {
        viewModelScope.launch {
            getAnnouncementDetailsUseCase(announcementId = state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) { announcementDetails ->
                        _state.update {
                            it.copy(
                                name = announcementDetails.authorName,
                                date = announcementDetails.updatedAt,
                                description =  announcementDetails.description,
                                attachments = announcementDetails.attachments,
                                uiState = ResourceDetailsContract.UiState.Success,
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
                        _state.update {
                            it.copy(
                                name = moduleDetails.name,
                                description = moduleDetails.description,
                                date = moduleDetails.updatedAt,
                                attachments = moduleDetails.attachments,
                                uiState = ResourceDetailsContract.UiState.Success,
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
                        _state.update {
                            it.copy(
                                name = assignmentDetails.name,
                                description = assignmentDetails.description,
                                date = assignmentDetails.dueDate,
                                attachments = assignmentDetails.attachments,
                                uiState = ResourceDetailsContract.UiState.Success,
                            )
                        }
                    }
                }
        }
    }

    private fun fetchQuizDetails() {
        viewModelScope.launch {
            combine(
                getQuizDetailsUseCase(state.value.resourceId),
                isQuizTakenUseCase(state.value.resourceId),
                ::Pair
            )
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) {
                        val (quizDetails, isQuizTaken) = it
                        _state.update { updatedState ->
                            updatedState.copy(
                                name = quizDetails.name,
                                date = quizDetails.updatedAt,
                                description = quizDetails.description,
                                startQuizDate = quizDetails.startDate,
                                endQuizDate = quizDetails.endDate,
                                quizDuration = quizDetails.duration,
                                quizType = quizDetails.quizType,
                                isQuizTaken = isQuizTaken,
                                uiState = ResourceDetailsContract.UiState.Success,
                            )
                        }
                    }
                }
        }
    }

    private inline fun <reified T> handleFetchResourceDetails(
        result: Result<T>,
        onSuccess: (T) -> Unit,
    ) {
        result.onLoading {
            onLoadingFetchResourceDetails()
        }.onSuccess { data ->
            onSuccess(data)
        }.onNoInternet { message ->
            onErrorFetchResourceDetails(message)
        }.onError { _, message ->
            onErrorFetchResourceDetails(message)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            onErrorFetchResourceDetails(message)
        }
    }

    private fun onLoadingFetchResourceDetails() {
        _state.update {
            it.copy(
                uiState = ResourceDetailsContract.UiState.Loading
            )
        }
    }

    private fun onErrorFetchResourceDetails(message: String) {
        _state.update {
            it.copy(
                uiState = ResourceDetailsContract.UiState.Error(message)
            )
        }
    }

    private fun fetchStudentWorks() {
        when (state.value.resourceType) {
            ClassResourceType.ASSIGNMENT -> fetchAssignmentSubmissions()
            ClassResourceType.QUIZ -> fetchQuizSubmissions()
            else -> return
        }
    }

    private fun fetchAssignmentSubmissions() {
        viewModelScope.launch {
            lecturerGetAssignmentSubmissionsUseCase(state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchStudentWorks(result)
                }
        }
    }

    private fun fetchQuizSubmissions() {
        viewModelScope.launch {
            getQuizSubmissionsUseCase(state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchStudentWorks(result)
                }
        }
    }

    private fun handleFetchStudentWorks(result: Result<List<StudentSubmissionModel>>) {
        result.onLoading {
            _state.update {
                it.copy(
                    studentWorkUiState = ResourceDetailsContract.UiState.Loading,
                )
            }
        }.onSuccess { submissions ->
            onSuccessFetchStudentWorks(submissions)
        }.onNoInternet { message ->
            onErrorFetchStudentWorks(message)
        }.onError { _, message ->
            onErrorFetchStudentWorks(message)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            onErrorFetchStudentWorks(message)
        }
    }

    private fun onSuccessFetchStudentWorks(submissions: List<StudentSubmissionModel>) {

        fun StudentSubmissionModel.toStudentSubmissionState() =
            ResourceDetailsContract.StudentSubmissionState(
                id = id,
                userId = userId,
                name = studentName,
            )

        val (submittedSubmissions, missingSubmissions) = submissions.partition { submission ->
            submission.turnInStatus
        }.let { submissionsPair ->
            Pair(
                submissionsPair.first.map { it.toStudentSubmissionState() },
                submissionsPair.second.map { it.toStudentSubmissionState() },
            )
        }

        _state.update {
            it.copy(
                submittedSubmissions = submittedSubmissions,
                missingSubmissions = missingSubmissions,
                studentWorkUiState = ResourceDetailsContract.UiState.Success,
            )
        }

        fetchProfilePics()
    }

    private fun onErrorFetchStudentWorks(message: String) {
        _state.update {
            it.copy(
                studentWorkUiState = ResourceDetailsContract.UiState.Error(message),
            )
        }
    }

    private fun fetchProfilePics() {
        viewModelScope.launch {
            state.value.submittedSubmissions.forEachIndexed { index, submission ->
                getProfilePicByIdUseCase(submission.userId)
                    .asResult()
                    .collect { result ->
                        handleFetchProfilePicSubmittedSubmissionResult(result, index)
                    }
            }

            state.value.missingSubmissions.forEachIndexed { index, submission ->
                getProfilePicByIdUseCase(submission.userId)
                    .asResult()
                    .collect { result ->
                        handleFetchProfilePicMissingSubmissionResult(result, index)
                    }
            }
        }
    }

    private fun handleFetchProfilePicSubmittedSubmissionResult(
        result: Result<Bitmap?>,
        index: Int,
    ) {
        result.onLoading {
            _state.update {
                it.copy(
                    submittedSubmissions = it.submittedSubmissions.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            _state.update {
                it.copy(
                    submittedSubmissions = it.submittedSubmissions.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic)
                        )
                    }.toList()
                )
            }
        }.onNoInternet { message ->
            onErrorFetchProfilePicSubmittedSubmission(message, index)
        }.onError { _, message ->
            onErrorFetchProfilePicSubmittedSubmission(message, index)
        }.onException { exception, _ ->
            onErrorFetchProfilePicSubmittedSubmission(exception?.message.toString(), index)
        }
    }

    private fun onErrorFetchProfilePicSubmittedSubmission(
        message: String,
        index: Int,
    ) {
        Log.e(TAG, message)
        _state.update {
            it.copy(
                submittedSubmissions = it.submittedSubmissions.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = PhotoProfileImageUiState.Success(null)
                    )
                }.toList()
            )
        }
    }

    private fun handleFetchProfilePicMissingSubmissionResult(
        result: Result<Bitmap?>,
        index: Int,
    ) {
        result.onLoading {
            _state.update {
                it.copy(
                    missingSubmissions = it.missingSubmissions.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            _state.update {
                it.copy(
                    missingSubmissions = it.missingSubmissions.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic)
                        )
                    }.toList()
                )
            }
        }.onNoInternet { message ->
            onErrorFetchProfilePicMissingSubmission(message, index)
        }.onError { _, message ->
            onErrorFetchProfilePicMissingSubmission(message, index)
        }.onException { exception, _ ->
            onErrorFetchProfilePicMissingSubmission(exception?.message.toString(), index)
        }
    }

    private fun onErrorFetchProfilePicMissingSubmission(
        message: String,
        index: Int,
    ) {
        Log.e(TAG, message)
        _state.update {
            it.copy(
                missingSubmissions = it.missingSubmissions.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = PhotoProfileImageUiState.Success(null)
                    )
                }.toList()
            )
        }
    }

    private fun fetchStudentAssignmentSubmission() {

        fun onErrorFetchStudentAssignmentSubmission(message: String) {
            _state.update {
                it.copy(
                    studentAssignmentBottomSheetUiState = ResourceDetailsContract.UiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            studentGetAssignmentSubmissionUseCase(state.value.resourceId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        _state.update {
                            it.copy(
                                studentAssignmentBottomSheetUiState = ResourceDetailsContract.UiState.Loading
                            )
                        }
                    }.onSuccess { assignmentSubmission ->
                        _state.update {
                            it.copy(
                                assignmentSubmission = assignmentSubmission,
                                studentAssignmentBottomSheetUiState = ResourceDetailsContract.UiState.Success,
                            )
                        }
                    }.onNoInternet { message ->
                        onErrorFetchStudentAssignmentSubmission(message)
                    }.onError { code, message ->
                        val notFoundErrorCode = "E444"
                        if (code.equals(notFoundErrorCode, true)) {
                            _state.update {
                                it.copy(
                                    studentAssignmentBottomSheetUiState = ResourceDetailsContract.UiState.Success,
                                )
                            }
                        } else {
                            onErrorFetchStudentAssignmentSubmission(message)
                        }
                    }.onException { exception, message ->
                        Log.e(TAG, exception?.message.toString())
                        onErrorFetchStudentAssignmentSubmission(message)
                    }
                }
        }
    }

    private fun showDeleteResourceDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showDeleteResourceDialog = show
                )
            )
        }
    }

    private fun deleteResource() {
        showDeleteResourceDialog(false)
        showDeletingResourceDialog(true)

        when (state.value.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> deleteAnnouncement()
            ClassResourceType.MODULE -> deleteModule()
            ClassResourceType.ASSIGNMENT -> deleteAssignment()
            ClassResourceType.QUIZ -> TODO()
        }
    }

    private fun deleteAnnouncement() {
        viewModelScope.launch {
            deleteAnnouncementUseCase(state.value.resourceId).asResult().collect { result ->
                handleDeleteResourceResult(result)
            }
        }
    }

    private fun deleteModule() {
        viewModelScope.launch {
            deleteModuleUseCase(state.value.resourceId).asResult().collect { result ->
                handleDeleteResourceResult(result)
            }
        }
    }

    private fun deleteAssignment() {
        viewModelScope.launch {
            deleteAssignmentUseCase(state.value.resourceId).asResult().collect { result ->
                handleDeleteResourceResult(result)
            }
        }
    }

    private fun handleDeleteResourceResult(result: Result<String>) {
        result.onLoading {
            onLoadingDeletingResource()
        }.onSuccess {
            onSuccessDeletingResource()
        }.onNoInternet { message ->
            onErrorDeletingResource(message)
        }.onError { _, message ->
            onErrorDeletingResource(message)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            onErrorDeletingResource(message)
        }
    }

    private fun onLoadingDeletingResource() {
        _state.update {
            it.copy(
                deletingResourceUiState = ResourceDetailsContract.UiState.Loading,
            )
        }
    }

    private fun onSuccessDeletingResource() {
        _state.update {
            it.copy(
                deletingResourceUiState = ResourceDetailsContract.UiState.Success,
            )
        }
    }

    private fun onErrorDeletingResource(message: String) {
        _state.update {
            it.copy(
                deletingResourceUiState = ResourceDetailsContract.UiState.Error(message),
            )
        }
    }

    private fun showDeletingResourceDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showDeletingResourceDialog = show
                )
            )
        }
    }

    private fun showAddWorkBottomSheet(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showAddWorkBottomSheet = show
                )
            )
        }
    }

    private fun navigateToCamera() {
        showAddWorkBottomSheet(false)
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.NavigateToCamera)
        }
    }

    private fun openFiles() {
        showAddWorkBottomSheet(false)
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.OpenFiles)
        }
    }

    private fun addAssignmentSubmissionAttachment(attachment: File) {
        _state.update {
            it.copy(
                assignmentSubmission = it.assignmentSubmission.copy(
                    attachments = it.assignmentSubmission.attachments.toMutableList().apply {
                        add(attachment)
                    }.toList()
                ),
            )
        }
        updateIsStudentCurrentWorkChange(true)
    }

    private fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                getCapturedPhotoUseCase().first()?.let { capturedPhoto ->
                    saveImageToFileUseCase(capturedPhoto).collect { imageFile ->
                        if (imageFile != null) {
                            addAssignmentSubmissionAttachment(imageFile)
                        } else {
                            showToast("Error capturing photo")
                        }
                    }
                }
            }.join()

            launch {
                resetPhotoUseCase
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.ShowToast(message))
        }
    }

    private fun openAttachment(attachment: File) {
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.OpenAttachment(attachment))
        }
    }

    private fun showStartQuizDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showStartQuizDialog = show
                )
            )
        }
    }

    private fun removeAssignmentSubmissionAttachment(index: Int) {
        _state.update {
            it.copy(
                assignmentSubmission = it.assignmentSubmission.copy(
                    attachments = it.assignmentSubmission.attachments.toMutableList().apply {
                        removeAt(index)
                    }.toList()
                ),
            )
        }
        updateIsStudentCurrentWorkChange(true)
    }

    private fun uploadAssignmentAttachments() {
        val uploadAssignmentAttachmentsStream =
            if (state.value.assignmentSubmission.assignmentSubmissionId.isEmpty()) {
                uploadAssignmentAttachmentsUseCase(
                    assignmentId = state.value.resourceId,
                    attachments = state.value.assignmentSubmission.attachments,
                )
            } else {
                updateAssignmentAttachmentsUseCase(
                    submissionId = state.value.assignmentSubmission.assignmentSubmissionId,
                    attachments = state.value.assignmentSubmission.attachments,
                )
            }

        viewModelScope.launch {
            uploadAssignmentAttachmentsStream.asResult().collect { result ->
                handleUploadAssignmentAttachmentResult(result)
            }
        }
    }

    private fun handleUploadAssignmentAttachmentResult(result: Result<String>) {

        fun updateIsSaveStudentCurrentWorkLoading(loading: Boolean) {
            _state.update {
                it.copy(
                    isSaveStudentCurrentWorkLoading = loading
                )
            }
        }

        result.onLoading {
            updateIsSaveStudentCurrentWorkLoading(true)
        }.onSuccess {
            showToast("Your current work successfully saved")
            updateIsSaveStudentCurrentWorkLoading(false)
            updateIsStudentCurrentWorkChange(false)
        }.onNoInternet { message ->
            showToast(message)
            updateIsSaveStudentCurrentWorkLoading(false)
        }.onError { _, message ->
            showToast(message)
            updateIsSaveStudentCurrentWorkLoading(false)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            showToast(message)
            updateIsSaveStudentCurrentWorkLoading(false)
        }
    }

    private fun updateIsStudentCurrentWorkChange(change: Boolean) {
        _state.update {
            it.copy(
                isStudentCurrentWorkChange = change,
            )
        }
    }

    private fun turnInAssignmentSubmission(turnIn: Boolean) {

        fun updateIsTurnInAssignmentSubmissionLoading(loading: Boolean) {
            _state.update {
                it.copy(
                    isTurnInAssignmentSubmissionLoading = loading
                )
            }
        }

        val successMessage = if (turnIn) {
            "Assignment turned in"
        } else {
            "Assignment has been unsubmitted"
        }

        viewModelScope.launch {
            updateIsTurnInAssignmentSubmissionLoading(true)

            if (turnIn && state.value.isStudentCurrentWorkChange) {
                launch {
                    updateAssignmentAttachmentsUseCase(
                        submissionId = state.value.assignmentSubmission.assignmentSubmissionId,
                        attachments = state.value.assignmentSubmission.attachments,
                    ).asResult().collect { result ->
                        handleUploadAssignmentAttachmentResult(result)
                    }
                }.join()
            }

            launch {
                turnInAssignmentSubmissionUseCase(
                    submissionId = state.value.assignmentSubmission.assignmentSubmissionId,
                    turnIn = turnIn,
                )
                    .asResult()
                    .onCompletion {
                        updateIsTurnInAssignmentSubmissionLoading(false)
                    }
                    .collect { result ->
                        result.onLoading {
                            updateIsTurnInAssignmentSubmissionLoading(true)
                        }.onSuccess {
                            showToast(successMessage)
                            _state.update {
                                it.copy(
                                    assignmentSubmission = it.assignmentSubmission.copy(
                                        turnInStatus = turnIn
                                    )
                                )
                            }
                        }.onNoInternet { message ->
                            showToast(message)
                        }.onError { _, message ->
                            showToast(message)
                        }.onException { exception, message ->
                            Log.e(TAG, exception?.message.toString())
                            showToast(message)
                        }
                }
            }
        }
    }

    private fun navigateToQuizSession() {
        viewModelScope.launch {
            _effect.emit(
                ResourceDetailsContract.Effect.NavigateToQuizSession(
                    quizId = state.value.resourceId,
                    quizTypeOrdinal = state.value.quizType.ordinal,
                    quizName = state.value.name,
                    quizDuration = state.value.quizDuration,
                )
            )
        }

        showStartQuizDialog(false)
    }

    private fun navigateToSubmissionDetails(
        submissionId: String,
        studentId: String,
        studentName: String,
    ) {
        viewModelScope.launch {
            val (submissionTypeOrdinal, resolvedSubmissionId) = when (state.value.resourceType) {
                ClassResourceType.ASSIGNMENT ->
                    SubmissionType.ASSIGNMENT.ordinal to submissionId

                ClassResourceType.QUIZ ->
                    SubmissionType.QUIZ.ordinal to state.value.resourceId

                else -> return@launch
            }

            _effect.emit(
                ResourceDetailsContract.Effect.NavigateToSubmissionDetails(
                    submissionTypeOrdinal = submissionTypeOrdinal,
                    submissionId = resolvedSubmissionId,
                    studentId = studentId,
                    studentName = studentName,
                )
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.NavigateBack)
        }
    }

    companion object {

        private const val TAG = "ResourceDetailsVM"
    }
}