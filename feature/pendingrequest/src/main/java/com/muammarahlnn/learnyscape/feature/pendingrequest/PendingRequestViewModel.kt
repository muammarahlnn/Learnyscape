package com.muammarahlnn.learnyscape.feature.pendingrequest

import android.util.Log
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
import com.muammarahlnn.learnyscape.core.domain.pendingrequest.CancelStudentRequestClassUseCase
import com.muammarahlnn.learnyscape.core.domain.pendingrequest.GetStudentPendingRequestClassesUseCase
import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.Effect
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.Event
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.State
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestViewModel, 29/02/2024 22.28
 */
@HiltViewModel
class PendingRequestViewModel @Inject constructor(
    private val getStudentPendingRequestClassesUseCase: GetStudentPendingRequestClassesUseCase,
    private val cancelStudentRequestClassUseCase: CancelStudentRequestClassUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            Event.FetchPendingRequestClasses -> fetchPendingRequestClasses()
            is Event.OnSelectCancelRequestClass -> onSelectCancelRequestClass(event.pendingRequest)
            Event.OnDismissCancelRequestClass -> onDismissCancelRequestClass()
            Event.OnCancelPendingRequestClass -> cancelStudentRequestClass()
        }
    }

    private fun fetchPendingRequestClasses() {

        fun onErrorFetchPendingRequestClasses(message: String) {
            updateState {
                it.copy(
                    uiState = UiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            getStudentPendingRequestClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            uiState = UiState.Loading
                        )
                    }
                }.onSuccess { pendingRequestClasses ->
                    updateState {
                        it.copy(
                            pendingRequestClasses = pendingRequestClasses,
                            uiState = if (pendingRequestClasses.isNotEmpty()) {
                                UiState.Success
                            } else {
                                UiState.SuccessEmpty
                            }
                        )
                    }
                }.onNoInternet { message ->
                    onErrorFetchPendingRequestClasses(message)
                }.onError { _, message ->
                    onErrorFetchPendingRequestClasses(message)
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    onErrorFetchPendingRequestClasses(message)
                }
            }
        }
    }

    private fun onSelectCancelRequestClass(pendingRequest: PendingRequestModel) {
        showCancelRequestClassDialog(true)
        updateState {
            it.copy(
                selectedPendingRequest = pendingRequest
            )
        }
    }

    private fun onDismissCancelRequestClass() {
        showCancelRequestClassDialog(false)
        updateState {
            it.copy(
                selectedPendingRequest = null
            )
        }
    }

    private fun cancelStudentRequestClass() {
        showCancelRequestClassDialog(false)

        state.value.selectedPendingRequest?.let { pendingRequest ->
            viewModelScope.launch {
                cancelStudentRequestClassUseCase(pendingRequest.classId)
                    .asResult()
                    .onCompletion {
                        fetchPendingRequestClasses()
                    }
                    .collect { result ->
                        result.onLoading {
                            updateState {
                                it.copy(
                                    uiState = UiState.Loading
                                )
                            }
                        }.onSuccess {
                            showToast("Class successfully canceled")
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

    private fun showCancelRequestClassDialog(show: Boolean) {
        updateState {
            it.copy(
                showCancelRequestClassDialog = show
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    private companion object {

        const val TAG = "PendingRequestViewModel"
    }
}