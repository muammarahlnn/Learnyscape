package com.muammarahlnn.submissiondetails

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
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByIdUseCase
import com.muammarahlnn.learnyscape.core.domain.submissiondetails.GetAssignmentSubmissionDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.submissiondetails.GetStudentQuizAnswersUseCase
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.submissiondetails.SubmissionDetailsContract.Effect
import com.muammarahlnn.submissiondetails.SubmissionDetailsContract.Event
import com.muammarahlnn.submissiondetails.SubmissionDetailsContract.State
import com.muammarahlnn.submissiondetails.SubmissionDetailsContract.UiState
import com.muammarahlnn.submissiondetails.navigation.SubmissionDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsViewModel, 03/02/2024 15.29
 */
@HiltViewModel
class SubmissionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssignmentSubmissionDetailsUseCase: GetAssignmentSubmissionDetailsUseCase,
    private val getStudentQuizAnswersUseCase: GetStudentQuizAnswersUseCase,
    private val getProfilePicByIdUseCase: GetProfilePicByIdUseCase,
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    private val args = SubmissionDetailsArgs(savedStateHandle)

    init {
        updateState {
            it.copy(
                submissionType = SubmissionType.getSubmissionType(args.submissionTypeOrdinal),
                submissionId = args.submissionId.orEmpty(),
                studentId = args.studentId,
                studentName = args.studentName,
                turnedInAt = args.turnedInAt,
            )
        }
    }

    override fun event(event: Event) {
        when (event) {
            Event.FetchSubmissionDetails ->
                fetchSubmissionDetails()

            is Event.OnAttachmentClick ->
                openAttachment(event.attachment)
        }
    }

    private fun fetchSubmissionDetails() {
        viewModelScope.launch {
            when (state.value.submissionType) {
                SubmissionType.ASSIGNMENT -> fetchStudentAssignmentSubmissionDetails()
                SubmissionType.QUIZ -> fetchStudentQuizAnswers()
            }

            fetchProfilePic()
        }
    }

    private suspend fun fetchStudentAssignmentSubmissionDetails() {
        getAssignmentSubmissionDetailsUseCase(state.value.submissionId)
            .asResult()
            .collect { result ->
                handleFetchSubmissionDetails(result) { assignmentSubmission ->
                    updateState {
                        it.copy(
                            assignmentSubmission = assignmentSubmission,
                            uiState = UiState.Success
                        )
                    }
                }
            }
    }

    private suspend fun fetchStudentQuizAnswers() {
        getStudentQuizAnswersUseCase(
            quizId = state.value.submissionId,
            studentId = state.value.studentId,
        ).asResult().collect { result ->
            handleFetchSubmissionDetails(result) { studentQuizAnswers ->
                updateState {
                    it.copy(
                        quizAnswers = studentQuizAnswers,
                        uiState = UiState.Success
                    )
                }
            }
        }
    }

    private inline fun <reified T> handleFetchSubmissionDetails(
        result: Result<T>,
        onSuccess: (T) -> Unit,
    ) {
        result.onLoading {
            updateState {
                it.copy(
                    uiState = UiState.Loading
                )
            }
        }.onSuccess {
            onSuccess(it)
        }.onNoInternet { message ->
            onErrorFetchSubmissionDetails(message)
        }.onError { _, message ->
            onErrorFetchSubmissionDetails(message)
        }.onException { exception, message ->
            Log.e(TAG, exception?.message.toString())
            onErrorFetchSubmissionDetails(message)
        }
    }

    private fun onErrorFetchSubmissionDetails(message: String) {
        updateState {
            it.copy(
                uiState = UiState.Error(message)
            )
        }
    }

    private suspend fun fetchProfilePic() {
        fun onErrorFetchProfilePic(message: String) {
            Log.e(TAG, message)
            updateState {
                it.copy(
                    profilePicUiState = PhotoProfileImageUiState.Success(null),
                )
            }
        }

        getProfilePicByIdUseCase(state.value.studentId)
            .asResult()
            .collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading,
                        )
                    }
                }.onSuccess { profilePic ->
                    updateState {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic)
                        )
                    }
                }.onNoInternet { message ->
                    onErrorFetchProfilePic(message)
                }.onError { _, message ->
                    onErrorFetchProfilePic(message)
                }.onException { exception, _ ->
                    onErrorFetchProfilePic(exception?.message.toString())
                }
            }
    }

    private fun openAttachment(attachment: File) {
        viewModelScope.emitEffect(Effect.OpenAttachment(attachment))
    }

    companion object {

        private const val TAG = "SubmissionDetailsVM"
    }
}