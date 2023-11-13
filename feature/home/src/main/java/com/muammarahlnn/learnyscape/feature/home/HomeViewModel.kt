package com.muammarahlnn.learnyscape.feature.home

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
import com.muammarahlnn.learnyscape.core.domain.GetClassesUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeViewModel, 12/10/2023 22.36 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getClasses: GetClassesUseCase,
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val homeUiState = _homeUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchClasses()
    }

    fun fetchClasses() {
        viewModelScope.launch {
            getClasses.execute(NoParams).asResult().collect { result ->
                result.onLoading {
                    _homeUiState.update {
                        HomeUiState.Loading
                    }
                }.onSuccess { classInfos ->
                    _homeUiState.update {
                        if (classInfos.isNotEmpty()) {
                            HomeUiState.Success(classInfos)
                        } else {
                            HomeUiState.SuccessEmptyClasses
                        }
                    }
                }.onNoInternet { message ->
                    _homeUiState.update {
                        HomeUiState.NoInternet(message)
                    }
                }.onError { _, message ->
                    _homeUiState.update {
                        HomeUiState.Error(message)
                    }
                }.onException { exception, message ->
                    Log.e("HomeViewModel", exception?.message.toString())
                    _homeUiState.update {
                        HomeUiState.Error(message)
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