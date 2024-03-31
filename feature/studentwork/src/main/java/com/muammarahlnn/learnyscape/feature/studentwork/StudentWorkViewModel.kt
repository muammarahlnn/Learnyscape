package com.muammarahlnn.learnyscape.feature.studentwork

import android.graphics.Bitmap
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
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByIdUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.LecturerGetAssignmentSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract.Effect
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract.Event
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract.State
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract.StudentSubmissionState
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentWorkViewModel, 22/02/2024 21.57
 */
@HiltViewModel
class StudentWorkViewModel @Inject constructor(
    private val lecturerGetAssignmentSubmissionsUseCase: LecturerGetAssignmentSubmissionsUseCase,
    private val getQuizSubmissionsUseCase: GetQuizSubmissionsUseCase,
    private val getProfilePicByIdUseCase: GetProfilePicByIdUseCase,
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    override fun event(event: Event) {
        when (event) {
            is Event.SetStudentWorkType -> setStudentWorkType(event.studentWorkType)
            is Event.SetResourceId -> setResourceId(event.resourceId)
            Event.FetchStudentWorks -> fetchStudentWorks()
        }
    }

    private fun setStudentWorkType(studentWorkType: StudentWorkType) {
        updateState {
            it.copy(
                studentWorkType = studentWorkType
            )
        }
    }

    private fun setResourceId(resourceId: String) {
        updateState {
            it.copy(
                resourceId = resourceId,
            )
        }
    }

    private fun fetchStudentWorks() {
        when (state.value.studentWorkType) {
            StudentWorkType.ASSIGNMENT -> fetchAssignmentSubmissions()
            StudentWorkType.QUIZ -> fetchQuizSubmissions()
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
                    uiState = UiState.Loading,
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

        fun StudentSubmissionModel.toStudentSubmissionState() = StudentSubmissionState(
            id = id,
            userId = userId,
            name = studentName,
            turnedInAt = turnedInAt,
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
                uiState = UiState.Success,
            )
        }

        fetchProfilePics()
    }

    private fun onErrorFetchStudentWorks(message: String) {
        updateState {
            it.copy(
                uiState = UiState.Error(message),
            )
        }
    }

    private fun fetchProfilePics() {
        viewModelScope.launch {
            state.value.submittedSubmissions.forEachIndexed { index, submission ->
                getProfilePicByIdUseCase(submission.userId)
                    .asResult()
                    .collect { result ->
                        handleFetchProfilePicResult(
                            submissionsPartition = SubmissionsPartition.SUBMITTED,
                            result = result,
                            index = index,
                        )
                    }
            }

            state.value.missingSubmissions.forEachIndexed { index, submission ->
                getProfilePicByIdUseCase(submission.userId)
                    .asResult()
                    .collect { result ->
                        handleFetchProfilePicResult(
                            submissionsPartition = SubmissionsPartition.MISSING,
                            result = result,
                            index = index,
                        )
                    }
            }
        }
    }

    private enum class SubmissionsPartition{
        SUBMITTED, MISSING
    }

    private fun handleFetchProfilePicResult(
        submissionsPartition: SubmissionsPartition,
        result: Result<Bitmap?>,
        index: Int,
    ) {
        result.onLoading {
            updateState {
                when (submissionsPartition) {
                    SubmissionsPartition.SUBMITTED -> it.copy(
                        submittedSubmissions = it.submittedSubmissions.updatePhotoProfileUiState(
                            profilePicUiState = PhotoProfileImageUiState.Loading,
                            index = index,
                        )
                    )

                    SubmissionsPartition.MISSING -> it.copy(
                        missingSubmissions = it.missingSubmissions.updatePhotoProfileUiState(
                            profilePicUiState = PhotoProfileImageUiState.Loading,
                            index = index,
                        )
                    )
                }
            }
        }.onSuccess { profilePic ->
            updateState {
                when (submissionsPartition) {
                    SubmissionsPartition.SUBMITTED -> it.copy(
                        submittedSubmissions = it.submittedSubmissions.updatePhotoProfileUiState(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic),
                            index = index,
                        )
                    )
                    
                    SubmissionsPartition.MISSING -> it.copy(
                        missingSubmissions = it.missingSubmissions.updatePhotoProfileUiState(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic),
                            index = index,
                        )
                    )
                }
            }
        }.onNoInternet { message -> 
            onErrorFetchProfilePic(
                submissionsPartition = submissionsPartition,
                message = message,
                index = index,
            )
        }.onError { _, message ->
            onErrorFetchProfilePic(
                submissionsPartition = submissionsPartition,
                message = message,
                index = index,
            )
        }.onException { exception, _ ->
            onErrorFetchProfilePic(
                submissionsPartition = submissionsPartition,
                message = exception?.message.toString(),
                index = index,
            )
        }
    }

    private fun onErrorFetchProfilePic(
        submissionsPartition: SubmissionsPartition,
        message: String,
        index: Int,
    ) {
        Log.e(TAG, message)
        updateState {
            when (submissionsPartition) {
                SubmissionsPartition.SUBMITTED -> it.copy(
                    submittedSubmissions = it.submittedSubmissions.updatePhotoProfileUiState(
                        profilePicUiState = PhotoProfileImageUiState.Success(null),
                        index = index,
                    )
                )
                
                SubmissionsPartition.MISSING -> it.copy(
                    missingSubmissions = it.missingSubmissions.updatePhotoProfileUiState(
                        profilePicUiState = PhotoProfileImageUiState.Success(null),
                        index = index,
                    )
                )
            }
        }
    }

    private fun List<StudentSubmissionState>.updatePhotoProfileUiState(
        profilePicUiState: PhotoProfileImageUiState,
        index: Int,
    ): List<StudentSubmissionState> = this.toMutableList().apply {
        this[index] = this[index].copy(
            profilePicUiState = profilePicUiState
        )
    }.toList()

    private companion object {

        const val TAG = "StudentWorkViewModel"
    }
}