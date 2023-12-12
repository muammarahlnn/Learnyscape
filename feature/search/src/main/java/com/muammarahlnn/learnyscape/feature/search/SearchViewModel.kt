package com.muammarahlnn.learnyscape.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.availableclass.GetAvailableClassesUseCase
import com.muammarahlnn.learnyscape.core.domain.availableclass.RequestJoinClassUseCase
import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
) : ViewModel(), SearchContract {

    private val _state = MutableStateFlow(SearchContract.State())
    override val state: StateFlow<SearchContract.State> = _state
    override fun event(event: SearchContract.Event) {
        when (event) {
            SearchContract.Event.FetchAvailableClasses -> fetchAvailableClasses()
            is SearchContract.Event.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            is SearchContract.Event.OnAvailableClassClick -> onAvailableClassClick(event.availableClass)
            SearchContract.Event.OnDismissJoinClass -> onDismissJoinRequestDialog()
            SearchContract.Event.OnRequestJoinClass -> requestJoinClass()
        }
    }

    private val _effect = MutableSharedFlow<SearchContract.Effect>()
    override val effect: SharedFlow<SearchContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing


    private fun fetchAvailableClasses() {
        viewModelScope.launch {
            getAvailableClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            uiState = SearchContract.UiState.Loading
                        )
                    }
                }.onSuccess { availableClasses ->
                    _state.update {
                        it.copy(
                            uiState = if (availableClasses.isNotEmpty()) {
                                SearchContract.UiState.Success(availableClasses)
                            } else {
                                SearchContract.UiState.SuccessEmpty
                            }
                        )
                    }
                }.onNoInternet { message ->
                    _state.update {
                        it.copy(
                            uiState = SearchContract.UiState.NoInternet(message)
                        )
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    _state.update {
                        it.copy(
                            uiState = SearchContract.UiState.Error(message)
                        )
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    _state.update {
                        it.copy(
                            uiState = SearchContract.UiState.Error(message)
                        )
                    }
                }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update {
            it.copy(searchQuery = query)
        }
    }

    private fun onAvailableClassClick(availableClass: AvailableClassModel) {
        _state.update {
            it.copy(
                selectedAvailableClass = availableClass,
                showJoinRequestDialog = true,
            )
        }
    }

    private fun requestJoinClass() {
        viewModelScope.launch {
            _state.value.selectedAvailableClass?.let { selectedAvailableClass ->
                requestJoinClassUseCase(selectedAvailableClass.id).asResult().collect { result ->
                    result.onLoading {
                        _state.update {
                            it.copy(joinRequestClassDialogLoading = true)
                        }
                    }.onSuccess {
                        _effect.emit(
                            SearchContract.Effect.ShowToast(
                                "Successfully requested to join ${selectedAvailableClass.name} class"
                            )
                        )
                        onDismissJoinRequestDialog()
                    }.onNoInternet { message ->
                        _effect.emit(SearchContract.Effect.ShowToast(message))
                        onDismissJoinRequestDialog()
                    }.onError { _, message ->
                        Log.e(TAG, message)
                        _effect.emit(SearchContract.Effect.ShowToast(message))
                        onDismissJoinRequestDialog()
                    }.onException { exception, message ->
                        Log.e(TAG, exception?.message.toString())
                        _effect.emit(SearchContract.Effect.ShowToast(message))
                        onDismissJoinRequestDialog()
                    }
                }
            }
        }
    }

    private fun onDismissJoinRequestDialog() {
        _state.update {
            it.copy(
                selectedAvailableClass = null,
                showJoinRequestDialog = false,
                joinRequestClassDialogLoading = false,
            )
        }
    }

    companion object {

        private const val TAG = "SearchViewModel"
    }
}
