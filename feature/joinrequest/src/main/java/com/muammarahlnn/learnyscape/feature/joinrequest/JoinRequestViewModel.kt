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
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.JoinRequestArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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
                    _state.update {
                        it.copy(
                            uiState = JoinRequestUiState.Loading
                        )
                    }
                }.onSuccess { waitingList ->
                    _state.update {
                        it.copy(
                            uiState = if (waitingList.isNotEmpty()) {
                                JoinRequestUiState.Success(waitingList)
                            } else {
                                JoinRequestUiState.SuccessEmpty
                            }
                        )
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

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(JoinRequestContract.Effect.NavigateBack)
        }
    }
}
