package com.muammarahlnn.learnyscape.feature.resourcedetails

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.contract.refresh
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
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.IsQuizTakenUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.LecturerGetAssignmentSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract.Effect
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract.Event
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract.State
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract.StudentSubmissionState
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract.UiState
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.ResourceDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
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
    private val isQuizTakenUseCase: IsQuizTakenUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    private val resourceDetailsArgs = ResourceDetailsArgs(savedStateHandle)

    init {
        updateState {
            it.copy(
                resourceId = resourceDetailsArgs.resourceId,
                resourceType = ClassResourceType.getClassResourceType(resourceDetailsArgs.resourceTypeOrdinal)
            )
        }
    }

    override fun event(event: Event) {
        when (event) {
            Event.FetchResourceDetails ->
                fetchResourceDetails()

            Event.FetchStudentWorks ->
                fetchStudentWorks()

            Event.OnDeleteClick ->
                showDeleteResourceDialog(true)

            Event.OnConfirmDeleteResourceDialog ->
                deleteResource()

            Event.OnDismissDeleteResourceDialog ->
                showDeleteResourceDialog(false)

            Event.OnConfirmSuccessDeletingResourceDialog ->
                showDeletingResourceDialog(false)

            Event.OnDismissDeletingResourceDialog ->
                showDeletingResourceDialog(false)

            is Event.OnAttachmentClick ->
                openAttachment(event.attachment)

            Event.OnStartQuizButtonClick ->
                showStartQuizDialog(true)

            Event.OnDismissStartQuizDialog ->
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
                        updateState {
                            it.copy(
                                name = announcementDetails.authorName,
                                date = announcementDetails.updatedAt,
                                description =  announcementDetails.description,
                                attachments = announcementDetails.attachments,
                                uiState = UiState.Success,
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
                                name = moduleDetails.name,
                                description = moduleDetails.description,
                                date = moduleDetails.updatedAt,
                                attachments = moduleDetails.attachments,
                                uiState = UiState.Success,
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
                                name = assignmentDetails.name,
                                description = assignmentDetails.description,
                                date = assignmentDetails.dueDate,
                                attachments = assignmentDetails.attachments,
                                uiState = UiState.Success,
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
                        updateState { updatedState ->
                            updatedState.copy(
                                name = quizDetails.name,
                                date = quizDetails.updatedAt,
                                description = quizDetails.description,
                                startQuizDate = quizDetails.startDate,
                                endQuizDate = quizDetails.endDate,
                                quizDuration = quizDetails.duration,
                                quizType = quizDetails.quizType,
                                isQuizTaken = isQuizTaken,
                                uiState = UiState.Success,
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
        updateState {
            it.copy(
                uiState = UiState.Loading
            )
        }
    }

    private fun onErrorFetchResourceDetails(message: String) {
        updateState {
            it.copy(
                uiState = UiState.Error(message)
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
            updateState {
                it.copy(
                    studentWorkUiState = UiState.Loading,
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
            StudentSubmissionState(
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

        updateState {
            it.copy(
                submittedSubmissions = submittedSubmissions,
                missingSubmissions = missingSubmissions,
                studentWorkUiState = UiState.Success,
            )
        }

        fetchProfilePics()
    }

    private fun onErrorFetchStudentWorks(message: String) {
        updateState {
            it.copy(
                studentWorkUiState = UiState.Error(message),
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
            updateState {
                it.copy(
                    submittedSubmissions = it.submittedSubmissions.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            updateState {
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
        updateState {
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
            updateState {
                it.copy(
                    missingSubmissions = it.missingSubmissions.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            updateState {
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
        updateState {
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
        updateState {
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
        updateState {
            it.copy(
                deletingResourceUiState = UiState.Loading,
            )
        }
    }

    private fun onSuccessDeletingResource() {
        updateState {
            it.copy(
                deletingResourceUiState = UiState.Success,
            )
        }
    }

    private fun onErrorDeletingResource(message: String) {
        updateState {
            it.copy(
                deletingResourceUiState = UiState.Error(message),
            )
        }
    }

    private fun showDeletingResourceDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showDeletingResourceDialog = show
                )
            )
        }
    }

    private fun openAttachment(attachment: File) {
        viewModelScope.emitEffect(Effect.OpenAttachment(attachment))
    }

    private fun showStartQuizDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showStartQuizDialog = show
                )
            )
        }
    }

    companion object {

        private const val TAG = "ResourceDetailsVM"
    }
}