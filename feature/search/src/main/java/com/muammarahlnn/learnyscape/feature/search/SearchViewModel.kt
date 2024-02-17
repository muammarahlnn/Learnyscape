package com.muammarahlnn.learnyscape.feature.search

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
import com.muammarahlnn.learnyscape.core.domain.availableclass.GetAvailableClassesUseCase
import com.muammarahlnn.learnyscape.core.domain.availableclass.RequestJoinClassUseCase
import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import com.muammarahlnn.learnyscape.feature.search.SearchContract.Effect
import com.muammarahlnn.learnyscape.feature.search.SearchContract.Event
import com.muammarahlnn.learnyscape.feature.search.SearchContract.State
import com.muammarahlnn.learnyscape.feature.search.SearchContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchViewModel, 13/11/2023 17.16 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAvailableClassesUseCase: GetAvailableClassesUseCase,
    private val requestJoinClassUseCase: RequestJoinClassUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            Event.FetchAvailableClasses -> fetchAvailableClasses()
            is Event.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            is Event.OnAvailableClassClick -> onAvailableClassClick(event.availableClass)
            Event.OnDismissJoinClass -> onDismissJoinRequestDialog()
            Event.OnRequestJoinClass -> requestJoinClass()
        }
    }

    private fun fetchAvailableClasses() {
        viewModelScope.launch {
            getAvailableClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            uiState = UiState.Loading
                        )
                    }
                }.onSuccess { availableClasses ->
                    updateState {
                        it.copy(
                            uiState = if (availableClasses.isNotEmpty()) {
                                UiState.Success(availableClasses)
                            } else {
                                UiState.SuccessEmpty
                            }
                        )
                    }
                }.onNoInternet { message ->
                    updateState {
                        it.copy(
                            uiState = UiState.NoInternet(message)
                        )
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    updateState {
                        it.copy(
                            uiState = UiState.Error(message)
                        )
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    updateState {
                        it.copy(
                            uiState = UiState.Error(message)
                        )
                    }
                }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        updateState {
            it.copy(searchQuery = query)
        }
    }

    private fun onAvailableClassClick(availableClass: AvailableClassModel) {
        updateState {
            it.copy(
                selectedAvailableClass = availableClass,
                showJoinRequestDialog = true,
            )
        }
    }

    private fun requestJoinClass() {
        state.value.selectedAvailableClass?.let { selectedAvailableClass ->
            viewModelScope.launch {

                fun onErrorRequestJoinClass(message: String) {
                    showToast(message)
                    onDismissJoinRequestDialog()
                }

                requestJoinClassUseCase(selectedAvailableClass.id).asResult().collect { result ->
                    result.onLoading {
                        updateState {
                            it.copy(joinRequestClassDialogLoading = true)
                        }
                    }.onSuccess {
                        showToast("Successfully requested to join ${selectedAvailableClass.name} class")
                        onDismissJoinRequestDialog()
                    }.onNoInternet { message ->
                        onErrorRequestJoinClass(message)
                    }.onError { _, message ->
                        Log.e(TAG, message)
                        onErrorRequestJoinClass(message)
                    }.onException { exception, message ->
                        Log.e(TAG, exception?.message.toString())
                        onErrorRequestJoinClass(message)
                    }
                }
            }
        }
    }

    private fun onDismissJoinRequestDialog() {
        updateState {
            it.copy(
                selectedAvailableClass = null,
                showJoinRequestDialog = false,
                joinRequestClassDialogLoading = false,
            )
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    companion object {

        private const val TAG = "SearchViewModel"
    }
}
