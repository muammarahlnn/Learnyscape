package com.muammarahlnn.learnyscape.feature.assignmentsubmission

import android.util.Log
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
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.StudentGetAssignmentSubmissionUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.TurnInAssignmentSubmissionUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.UpdateAssignmentAttachmentsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.UploadAssignmentAttachmentsUseCase
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract.Effect
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract.Event
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract.State
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionViewModel, 19/02/2024 18.47
 */
@HiltViewModel
class AssignmentSubmissionViewModel @Inject constructor(
    private val studentGetAssignmentSubmissionUseCase: StudentGetAssignmentSubmissionUseCase,
    private val uploadAssignmentAttachmentsUseCase: UploadAssignmentAttachmentsUseCase,
    private val updateAssignmentAttachmentsUseCase: UpdateAssignmentAttachmentsUseCase,
    private val turnInAssignmentSubmissionUseCase: TurnInAssignmentSubmissionUseCase,
    private val getCapturedPhotoUseCase: GetCapturedPhotoUseCase,
    private val resetPhotoUseCase: ResetCapturedPhotoUseCase,
    private val saveImageToFileUseCase: SaveImageToFileUseCase,
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    override fun event(event: Event) {
        when (event) {
            is Event.SetAssignmentId ->
                setAssignmentId(event.assignmentId)

            is Event.SetIsAssignmentDeleted ->
                setIsAssignmentDelete(event.isAssignmentDeleted)

            Event.FetchStudentSubmission ->
                fetchStudentSubmission()

            is Event.OnAttachmentClick ->
                openAttachment(event.attachment)

            Event.OnUploadFileActionClick -> {
                openFiles()
                showAddWorkBottomSheet(false)
            }

            is Event.OnFileSelected ->
                addSubmissionAttachment(event.file)

            Event.OnGetCapturedPhoto ->
                getCapturedPhoto()

            is Event.OnRemoveAssignment ->
                removeSubmissionAttachment(event.index)

            Event.OnSaveStudentCurrentWorkClick ->
                uploadAssignmentAttachments()

            Event.OnTurnInSubmission ->
                turnInSubmission(true)

            Event.OnUnsubmitSubmission ->
                turnInSubmission(false)

            is Event.OnShowAddWorkBottomSheet ->
                showAddWorkBottomSheet(event.show)

            is Event.OnShowSaveYourWorkInfoDialog ->
                showSaveYourWorkInfoDialog(event.show)

            is Event.OnShowTurnInDialog ->
                showTurnInDialog(event.show)

            is Event.OnShowUnsubmitDialog ->
                showUnsubmitDialog(event.show)
        }
    }

    private fun setAssignmentId(assignmentId: String) {
        updateState {
            it.copy(
                assignmentId = assignmentId,
            )
        }
    }

    private fun setIsAssignmentDelete(isAssignmentDeleted: Boolean) {
        updateState {
            it.copy(
                isAssignmentDeleted = isAssignmentDeleted,
            )
        }
    }

    private fun fetchStudentSubmission() {

        fun onErrorFetchStudentSubmission(message: String) {
            updateState {
                it.copy(
                    uiState = UiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            studentGetAssignmentSubmissionUseCase(state.value.assignmentId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        updateState {
                            it.copy(
                                uiState = UiState.Loading
                            )
                        }
                    }.onSuccess { assignmentSubmission ->
                        updateState {
                            it.copy(
                                submission = assignmentSubmission,
                                uiState = UiState.Success,
                            )
                        }
                    }.onNoInternet { message ->
                        onErrorFetchStudentSubmission(message)
                    }.onError { code, message ->
                        val notFoundErrorCode = "E444"
                        if (code.equals(notFoundErrorCode, true)) {
                            updateState {
                                it.copy(
                                    uiState = UiState.Success,
                                )
                            }
                        } else {
                            onErrorFetchStudentSubmission(message)
                        }
                    }.onException { exception, message ->
                        Log.e(TAG, exception?.message.toString())
                        onErrorFetchStudentSubmission(message)
                    }
                }
        }
    }

    private fun uploadAssignmentAttachments() {
        val uploadAssignmentAttachmentStream =
            if (state.value.submission.assignmentSubmissionId.isEmpty()) {
                uploadAssignmentAttachmentsUseCase(
                    assignmentId = state.value.assignmentId,
                    attachments = state.value.submission.attachments,
                )
            } else {
                updateAssignmentAttachmentsUseCase(
                    submissionId = state.value.submission.assignmentSubmissionId,
                    attachments = state.value.submission.attachments,
                )
            }

        viewModelScope.launch {
            uploadAssignmentAttachmentStream.asResult().collect { result ->
                handleUploadAssignmentAttachmentResult(result)
            }
        }
    }

    private fun handleUploadAssignmentAttachmentResult(result: Result<String>) {

        fun setIsSaveStudentCurrentWorkLoading(loading: Boolean) {
            updateState {
                it.copy(
                    isSaveStudentCurrentWorkLoading = loading
                )
            }
        }

        fun onErrorUploadAssignmentAttachment(message: String) {
            showToast(message)
            setIsSaveStudentCurrentWorkLoading(false)
        }

        result.onLoading {
            setIsSaveStudentCurrentWorkLoading(true)
        }.onSuccess {
            showToast("Your current work successfully saved")
            setIsSaveStudentCurrentWorkLoading(false)
            setIsStudentCurrentWorkChange(false)
        }.onNoInternet { message ->
            onErrorUploadAssignmentAttachment(message)
        }.onError { _, message ->
            onErrorUploadAssignmentAttachment(message)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            onErrorUploadAssignmentAttachment(message)
        }
    }

    private fun turnInSubmission(turnIn: Boolean) {
        if (turnIn) {
            showTurnInDialog(false)
        } else {
            showUnsubmitDialog(false)
        }

        fun setIsTurnInSubmissionLoading(loading: Boolean) {
            updateState {
                it.copy(
                    isTurnInSubmissionLoading = loading
                )
            }
        }

        val successMessage = if (turnIn) "Assignment turned in" else "Assignment has been unsubmitted"
        viewModelScope.launch {
            setIsTurnInSubmissionLoading(true)

            if (turnIn && state.value.isStudentCurrentWorkChange) {
                launch {
                    updateAssignmentAttachmentsUseCase(
                        submissionId = state.value.submission.assignmentSubmissionId,
                        attachments = state.value.submission.attachments,
                    ).asResult().collect { result ->
                        handleUploadAssignmentAttachmentResult(result)
                    }
                }.join()
            }

            launch {
                turnInAssignmentSubmissionUseCase(
                    submissionId = state.value.submission.assignmentSubmissionId,
                    turnIn = turnIn,
                )
                    .asResult()
                    .onCompletion {
                        setIsTurnInSubmissionLoading(false)
                    }
                    .collect { result ->
                        result.onLoading {
                            setIsTurnInSubmissionLoading(true)
                        }.onSuccess {
                            showToast(successMessage)
                            updateState {
                                it.copy(
                                    submission = it.submission.copy(
                                        turnInStatus = turnIn,
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

    private fun addSubmissionAttachment(attachment: File) {
        updateState {
            it.copy(
                submission = it.submission.copy(
                    attachments = it.submission.attachments.toMutableList().apply {
                        add(attachment)
                    }.toList()
                )
            )
        }
        setIsStudentCurrentWorkChange(true)
    }

    private fun getCapturedPhoto() {
        viewModelScope.launch {
            launch {
                getCapturedPhotoUseCase().first()?.let { capturedPhoto ->
                    saveImageToFileUseCase(capturedPhoto).collect { imageFile ->
                        if (imageFile != null) {
                            addSubmissionAttachment(imageFile)
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

    private fun removeSubmissionAttachment(index: Int) {
        updateState {
            it.copy(
                submission = it.submission.copy(
                    attachments = it.submission.attachments.toMutableList().apply {
                        removeAt(index)
                    }.toList()
                )
            )
        }
        setIsStudentCurrentWorkChange(true)
    }

    private fun setIsStudentCurrentWorkChange(change: Boolean) {
        updateState {
            it.copy(
                isStudentCurrentWorkChange = change,
            )
        }
    }

    private fun openFiles() {
        viewModelScope.emitEffect(Effect.OpenFiles)
    }

    private fun openAttachment(attachment: File) {
        viewModelScope.emitEffect(Effect.OpenAttachment(attachment))
    }

    private fun showAddWorkBottomSheet(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showAddWorkBottomSheet = show
                )
            )
        }
    }

    private fun showTurnInDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showTurnInDialog = show
                )
            )
        }
    }

    private fun showSaveYourWorkInfoDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showSaveYourWorkInfoDialog = show
                )
            )
        }
    }

    private fun showUnsubmitDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showUnsubmitDialog = show
                )
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    private companion object {

        const val TAG = "AssignmentSubmissionVM"
    }
}