package com.muammarahlnn.submissiondetails

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
import com.muammarahlnn.learnyscape.core.domain.submissiondetails.GetAssignmentSubmissionDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.submissiondetails.GetStudentQuizAnswersUseCase
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.submissiondetails.navigation.SubmissionDetailsArgs
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
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsViewModel, 03/02/2024 15.29
 */
@HiltViewModel
class SubmissionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssignmentSubmissionDetailsUseCase: GetAssignmentSubmissionDetailsUseCase,
    private val getStudentQuizAnswersUseCase: GetStudentQuizAnswersUseCase,
) : ViewModel(), SubmissionDetailsContract {

    private val args = SubmissionDetailsArgs(savedStateHandle)

    private val _state = MutableStateFlow(SubmissionDetailsContract.State(
        submissionType = SubmissionType.getSubmissionType(args.submissionTypeOrdinal),
        submissionId = args.submissionId.orEmpty(),
        studentId = args.studentId,
        studentName = args.studentName,
    ))
    override val state: StateFlow<SubmissionDetailsContract.State> = _state

    private val _effect = MutableSharedFlow<SubmissionDetailsContract.Effect>()
    override val effect: SharedFlow<SubmissionDetailsContract.Effect> = _effect

    override fun event(event: SubmissionDetailsContract.Event) {
        when (event) {
            SubmissionDetailsContract.Event.FetchSubmissionDetails ->
                fetchSubmissionDetails()

            is SubmissionDetailsContract.Event.OnAttachmentClick ->
                openAttachment(event.attachment)

            SubmissionDetailsContract.Event.OnBackClick ->
                navigateBack()
        }
    }

    private fun fetchSubmissionDetails() {
        viewModelScope.launch {
            when (state.value.submissionType) {
                SubmissionType.ASSIGNMENT -> fetchStudentAssignmentSubmissionDetails()
                SubmissionType.QUIZ -> fetchStudentQuizAnswers()
            }
        }
    }

    private suspend fun fetchStudentAssignmentSubmissionDetails() {
        getAssignmentSubmissionDetailsUseCase(state.value.submissionId)
            .asResult()
            .collect { result ->
                handleFetchSubmissionDetails(result) { assignmentSubmission ->
                    _state.update {
                        it.copy(
                            assignmentSubmission = assignmentSubmission,
                            uiState = SubmissionDetailsContract.UiState.Success
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
                _state.update {
                    it.copy(
                        quizAnswers = studentQuizAnswers,
                        uiState = SubmissionDetailsContract.UiState.Success
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
            _state.update {
                it.copy(
                    uiState = SubmissionDetailsContract.UiState.Loading
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
        _state.update {
            it.copy(
                uiState = SubmissionDetailsContract.UiState.Error(message)
            )
        }
    }

    private fun openAttachment(attachment: File) {
        viewModelScope.launch {
            _effect.emit(SubmissionDetailsContract.Effect.OpenAttachment(attachment))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(SubmissionDetailsContract.Effect.NavigateBack)
        }
    }

    companion object {

        private const val TAG = "SubmissionDetailsVM"
    }
}