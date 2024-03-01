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
import com.muammarahlnn.learnyscape.core.domain.pendingrequest.GetStudentPendingRequestClassesUseCase
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.Effect
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.Event
import com.muammarahlnn.learnyscape.feature.pendingrequest.PendingRequestContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestViewModel, 29/02/2024 22.28
 */
@HiltViewModel
class PendingRequestViewModel @Inject constructor(
    private val getStudentPendingRequestClassesUseCase: GetStudentPendingRequestClassesUseCase,
) : ViewModel(),
    ContractProvider<UiState, Event, Effect> by contract(UiState.Loading),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            Event.FetchPendingRequestClasses -> fetchPendingRequestClasses()
        }
    }

    private fun fetchPendingRequestClasses() {

        fun onErrorFetchPendingRequestClasses(message: String) {
            updateState {
                UiState.Error(message)
            }
        }

        viewModelScope.launch {
            getStudentPendingRequestClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        UiState.Loading
                    }
                }.onSuccess { pendingRequestClasses ->
                    updateState {
                        if (pendingRequestClasses.isNotEmpty()) {
                            UiState.Success(pendingRequestClasses)
                        } else {
                            UiState.SuccessEmpty
                        }
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

    private companion object {

        const val TAG = "PendingRequestViewModel"
    }
}