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
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByIdUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteAnnouncementUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteAssignmentUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteModuleUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAnnouncementDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAssignmentDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAssignmentSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.ResourceDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val getAssignmentSubmissionsUseCase: GetAssignmentSubmissionsUseCase,
    private val getProfilePicByIdUseCase: GetProfilePicByIdUseCase,
    private val getQuizSubmissionsUseCase: GetQuizSubmissionsUseCase,
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

            is ResourceDetailsContract.Event.OnFileSelected -> TODO()


            is ResourceDetailsContract.Event.OnAttachmentClick ->
                openAttachment(event.attachment)

            ResourceDetailsContract.Event.OnDismissAddWorkBottomSheet ->
                showAddWorkBottomSheet(false)

            ResourceDetailsContract.Event.OnStartQuizButtonClick ->
                showStartQuizDialog(true)

            is ResourceDetailsContract.Event.OnConfirmStartQuizDialog ->
                navigateToQuizSession(
                    event.quizTypeOrdinal,
                    event.quizName,
                    event.quizDuration,
                )

            ResourceDetailsContract.Event.OnDismissStartQuizDialog ->
                showStartQuizDialog(false)
        }
    }

    private fun fetchResourceDetails() {
        when (state.value.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> fetchAnnouncementDetails()

            ClassResourceType.MODULE -> fetchModuleDetails()

            ClassResourceType.ASSIGNMENT -> {
                fetchAssignmentDetails()
                fetchStudentWorks()
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
            getQuizDetailsUseCase(quizId = state.value.resourceId)
                .asResult()
                .collect { result ->
                    handleFetchResourceDetails(result) { quizDetails ->
                        _state.update {
                            it.copy(
                                name = quizDetails.name,
                                date = quizDetails.updatedAt,
                                description = quizDetails.description,
                                startQuizDate = quizDetails.startDate,
                                endQuizDate = quizDetails.endDate,
                                quizDuration = quizDetails.duration,
                                quizType = quizDetails.quizType,
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
            getAssignmentSubmissionsUseCase(state.value.resourceId)
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
                deletingResourceUiState = ResourceDetailsContract.DeletingResourceDialogState.Loading,
            )
        }
    }

    private fun onSuccessDeletingResource() {
        _state.update {
            it.copy(
                deletingResourceUiState = ResourceDetailsContract.DeletingResourceDialogState.Success,
            )
        }
    }

    private fun onErrorDeletingResource(message: String) {
        _state.update {
            it.copy(
                deletingResourceUiState = ResourceDetailsContract.DeletingResourceDialogState.Error(message),
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

    private fun navigateToQuizSession(
        quizTypeOrdinal: Int,
        quizName: String,
        quizDuration: Int,
    ) {
        viewModelScope.launch {
            _effect.emit(
                ResourceDetailsContract.Effect.NavigateToQuizSession(
                    quizTypeOrdinal = quizTypeOrdinal,
                    quizName = quizName,
                    quizDuration = quizDuration,
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