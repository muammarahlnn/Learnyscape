package com.muammarahlnn.learnyscape.feature.search.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
import com.muammarahlnn.learnyscape.feature.search.uistate.JoinRequestClassDialogUiState
import com.muammarahlnn.learnyscape.feature.search.uistate.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchViewModel, 13/11/2023 17.16 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAvailableClassesUseCase: GetAvailableClassesUseCase,
    private val requestJoinClassUseCase: RequestJoinClassUseCase,
) : ViewModel() {

    var selectedAvailableClass: AvailableClassModel? = null
        private set

    var showJoinRequestDialog by mutableStateOf(false)
        private set

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)

    val searchUiState = _searchUiState.asStateFlow()

    private val _joinRequestDialogUiState = MutableStateFlow<JoinRequestClassDialogUiState>(JoinRequestClassDialogUiState.None)

    val joinRequestDialogUiState = _joinRequestDialogUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchAvailableClasses()
    }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onAvailableClassClick(availableClass: AvailableClassModel) {
        selectedAvailableClass = availableClass
        showJoinRequestDialog = true
    }

    fun onDismissJoinRequestDialog() {
        selectedAvailableClass = null
        showJoinRequestDialog = false
        _joinRequestDialogUiState.update {
            JoinRequestClassDialogUiState.None
        }
    }

    fun fetchAvailableClasses() {
        viewModelScope.launch {
            getAvailableClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    _searchUiState.update {
                        SearchUiState.Loading
                    }
                }.onSuccess { availableClasses ->
                    _searchUiState.update {
                        if (availableClasses.isNotEmpty()) {
                            SearchUiState.Success(availableClasses)
                        } else {
                            SearchUiState.SuccessEmpty
                        }
                    }
                }.onNoInternet { message ->
                    _searchUiState.update {
                        SearchUiState.NoInternet(message)
                    }
                }.onError { _, message ->
                    _searchUiState.update {
                        SearchUiState.Error(message)
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    _searchUiState.update {
                        SearchUiState.Error(message)
                    }
                }
            }
        }
    }

    fun requestJoinClass() {
        viewModelScope.launch {
            // double bang operator used because it's guaranteed selectedAvailableClass will never be null
            requestJoinClassUseCase(selectedAvailableClass?.id!!).asResult().collect { result ->
                result.onLoading {
                    _joinRequestDialogUiState.update {
                        JoinRequestClassDialogUiState.Loading
                    }
                }.onSuccess {
                    _joinRequestDialogUiState.update {
                        JoinRequestClassDialogUiState.Success
                    }
                }.onNoInternet { message ->
                    _joinRequestDialogUiState.update {
                        JoinRequestClassDialogUiState.NoInternet(message)
                    }
                }.onError { _, message ->
                    _joinRequestDialogUiState.update {
                        JoinRequestClassDialogUiState.Error(message)
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    _joinRequestDialogUiState.update {
                        JoinRequestClassDialogUiState.Error(message)
                    }
                }
            }
        }
    }

    companion object {

        private const val TAG = "SearchViewModel"

        private const val SEARCH_QUERY = "search_query"
    }
}
