package com.muammarahlnn.learnyscape.feature.joinrequest

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
import com.muammarahlnn.learnyscape.core.domain.joinrequest.GetWaitingClassUseCase
import com.muammarahlnn.learnyscape.core.domain.joinrequest.PutStudentAcceptanceUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByIdUseCase
import com.muammarahlnn.learnyscape.core.model.data.WaitingListModel
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.JoinRequestArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestViewModel, 17/01/2024 22.05
 */
@HiltViewModel
class JoinRequestViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWaitingClassUseCase: GetWaitingClassUseCase,
    private val putStudentAcceptanceUseCase: PutStudentAcceptanceUseCase,
    private val getProfilePicByIdUseCase: GetProfilePicByIdUseCase,
) : ViewModel(), JoinRequestContract {

    private val joinRequestArgs = JoinRequestArgs(savedStateHandle)

    private val _state = MutableStateFlow(
        JoinRequestContract.State(classId = joinRequestArgs.classId)
    )
    override val state: StateFlow<JoinRequestContract.State> = _state

    private val _effect = MutableSharedFlow<JoinRequestContract.Effect>()
    override val effect: SharedFlow<JoinRequestContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    override fun event(event: JoinRequestContract.Event) {
        when (event) {
            JoinRequestContract.Event.FetchWaitingList ->
                fetchWaitingList()

            JoinRequestContract.Event.OnCloseClick ->
                navigateBack()

            is JoinRequestContract.Event.OnAcceptStudent ->
                updateStudentAcceptance(event.studentId, true)

            is JoinRequestContract.Event.OnRejectStudent ->
                updateStudentAcceptance(event.studentId, false)
        }
    }

    private fun fetchWaitingList() {
        fun onErrorFetchWaitingList(message: String) {
            _state.update {
                it.copy(
                    uiState = JoinRequestUiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            getWaitingClassUseCase(classId = _state.value.classId).asResult().collect { result ->
                result.onLoading {
                    onUiStateLoading()
                }.onSuccess { waitingList ->
                    if (waitingList.isNotEmpty()) {
                        _state.update {
                            it.copy(
                                uiState = JoinRequestUiState.Success,
                                waitingListStudents = waitingList.map { waitingListModel ->
                                    waitingListModel.toWaitingListStudentState()
                                }
                            )
                        }
                        fetchProfilePics()
                    } else {
                        _state.update {
                            it.copy(
                                uiState = JoinRequestUiState.SuccessEmpty
                            )
                        }
                    }
                }.onNoInternet { message ->
                    onErrorFetchWaitingList(message)
                }.onError { _, message ->
                    onErrorFetchWaitingList(message)
                }.onException { exception, message ->
                    Log.e("JoinRequestViewModel", exception?.message.toString())
                    onErrorFetchWaitingList(message)
                }
            }
        }
    }

    private fun WaitingListModel.toWaitingListStudentState(): JoinRequestContract.WaitingListStudentState =
        JoinRequestContract.WaitingListStudentState(
            id = id,
            userId = userId,
            name = fullName,
        )

    private fun fetchProfilePics() {
        viewModelScope.launch {
            state.value.waitingListStudents.forEachIndexed { index, student ->
                getProfilePicByIdUseCase(student.userId)
                    .asResult()
                    .collect { result ->
                        result.onLoading {
                            _state.update {
                                it.copy(
                                    waitingListStudents = it.waitingListStudents.toMutableList().apply {
                                        this[index] = this[index].copy(
                                            profilePicUiState = PhotoProfileImageUiState.Loading
                                        )
                                    }.toList()
                                )
                            }
                        }.onSuccess { profilePic ->
                            _state.update {
                                it.copy(
                                    waitingListStudents = it.waitingListStudents.toMutableList().apply {
                                        this[index] = this[index].copy(
                                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic)
                                        )
                                    }.toList()
                                )
                            }
                        }.onNoInternet { message ->
                            onErrorFetchProfilePicWaitingList(message, index)
                        }.onError { _, message ->
                            onErrorFetchProfilePicWaitingList(message, index)
                        }.onException { exception, _ ->
                            onErrorFetchProfilePicWaitingList(exception?.message.toString(), index)
                        }
                    }
            }
        }
    }

    private fun onErrorFetchProfilePicWaitingList(
        message: String,
        index: Int,
    ) {
        Log.e(TAG, message)
        _state.update {
            it.copy(
                waitingListStudents = it.waitingListStudents.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = PhotoProfileImageUiState.Success(null)
                    )
                }.toList()
            )
        }
    }

    private fun updateStudentAcceptance(
        studentId: String,
        accepted: Boolean,
    ) {
        putStudentAcceptanceUseCase(
            studentId = studentId,
            accepted = accepted,
        ).asResult().onEach { result ->
            result.onLoading {
                onUiStateLoading()
            }.onSuccess {
                val message = "Successfully ${if (accepted) "accepted" else "rejected"} student join request"
                showToast(message)
            }.onNoInternet { message ->
                showToast(message)
            }.onError { _, message ->
                showToast(message)
            }.onException { exception, message ->
                Log.e(TAG, exception?.message.toString())
                showToast(message)
            }
        }.onCompletion {
            fetchWaitingList()
        }.launchIn(viewModelScope)
    }

    private fun onUiStateLoading() {
        _state.update {
            it.copy(
                uiState = JoinRequestUiState.Loading
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _effect.emit(JoinRequestContract.Effect.ShowToast(message))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(JoinRequestContract.Effect.NavigateBack)
        }
    }

    companion object {

        private const val TAG = "JoinRequestViewModel"
    }
}
