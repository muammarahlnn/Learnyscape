package com.muammarahlnn.learnyscape.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.home.GetEnrolledClassesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeViewModel, 12/10/2023 22.36 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEnrolledClassesUseCase: GetEnrolledClassesUseCase
) : ViewModel(), HomeContract {

    private val _state = MutableStateFlow(HomeContract.State())
    override val state: StateFlow<HomeContract.State> = _state

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    override fun event(event: HomeContract.Event) = when (event) {
        HomeContract.Event.FetchEnrolledClasses -> fetchEnrolledClasses()
        is HomeContract.Event.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
    }

    private fun fetchEnrolledClasses() {
        viewModelScope.launch {
            getEnrolledClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(uiState = HomeContract.UiState.Loading)
                    }
                }.onSuccess { enrolledClasses ->
                    _state.update {
                        it.copy(
                            uiState = if (enrolledClasses.isNotEmpty()) {
                                HomeContract.UiState.Success(enrolledClasses)
                            } else {
                                HomeContract.UiState.SuccessEmptyClasses
                            }
                        )
                    }
                }.onNoInternet { message ->
                    _state.update {
                        it.copy(uiState = HomeContract.UiState.NoInternet(message))
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    _state.update {
                        it.copy(uiState = HomeContract.UiState.Error(message))
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    _state.update {
                        it.copy(uiState = HomeContract.UiState.Error(message))
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

    companion object {

        private const val TAG = "HomeViewModel"
    }
}