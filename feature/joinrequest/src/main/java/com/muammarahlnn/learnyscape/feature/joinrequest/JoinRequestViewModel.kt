package com.muammarahlnn.learnyscape.feature.joinrequest

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.contract.refresh
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
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestContract.Effect
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestContract.Event
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestContract.State
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestContract.UiState
import com.muammarahlnn.learnyscape.feature.joinrequest.JoinRequestContract.WaitingListStudentState
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.JoinRequestArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
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
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    private val args = JoinRequestArgs(savedStateHandle)

    init {
        updateState {
            it.copy(
                classId = args.classId,
            )
        }
    }

    override fun event(event: Event) {
        when (event) {
            Event.FetchWaitingList ->
                fetchWaitingList()

            is Event.OnAcceptStudent ->
                updateStudentAcceptance(event.studentId, true)

            is Event.OnRejectStudent ->
                updateStudentAcceptance(event.studentId, false)
        }
    }

    private fun fetchWaitingList() {
        fun onErrorFetchWaitingList(message: String) {
            updateState {
                it.copy(
                    uiState = UiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            getWaitingClassUseCase(classId = state.value.classId).asResult().collect { result ->
                result.onLoading {
                    onUiStateLoading()
                }.onSuccess { waitingList ->
                    if (waitingList.isNotEmpty()) {
                        updateState {
                            it.copy(
                                uiState = UiState.Success,
                                waitingListStudents = waitingList.map { waitingListModel ->
                                    waitingListModel.toWaitingListStudentState()
                                }
                            )
                        }
                        fetchProfilePics()
                    } else {
                        updateState {
                            it.copy(
                                uiState = UiState.SuccessEmpty
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

    private fun WaitingListModel.toWaitingListStudentState(): WaitingListStudentState =
        WaitingListStudentState(
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
                            updateState {
                                it.copy(
                                    waitingListStudents = it.waitingListStudents.toMutableList().apply {
                                        this[index] = this[index].copy(
                                            profilePicUiState = PhotoProfileImageUiState.Loading
                                        )
                                    }.toList()
                                )
                            }
                        }.onSuccess { profilePic ->
                            updateState {
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
        updateState {
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
        updateState {
            it.copy(
                uiState = UiState.Loading
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    companion object {

        private const val TAG = "JoinRequestViewModel"
    }
}
