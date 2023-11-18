package com.muammarahlnn.learnyscape.feature.search

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
import com.muammarahlnn.learnyscape.core.domain.availableclass.GetAvailableClassesUseCase
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
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)

    val searchUiState = _searchUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchAvailableClasses()
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
                    Log.e("SearchViewModel", exception?.message.toString())
                    _searchUiState.update {
                        SearchUiState.Error(message)
                    }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }
}

private const val SEARCH_QUERY = "search_query"