package com.muammarahlnn.learnyscape.feature.home

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
import com.muammarahlnn.learnyscape.core.domain.home.GetEnrolledClassesUseCase
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Effect
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Event
import com.muammarahlnn.learnyscape.feature.home.HomeContract.State
import com.muammarahlnn.learnyscape.feature.home.HomeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeViewModel, 12/10/2023 22.36 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEnrolledClassesUseCase: GetEnrolledClassesUseCase
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            Event.FetchEnrolledClasses -> fetchEnrolledClasses()
            is Event.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    private fun fetchEnrolledClasses() {
        viewModelScope.launch {
            getEnrolledClassesUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(uiState = UiState.Loading)
                    }
                }.onSuccess { enrolledClasses ->
                    updateState {
                        it.copy(
                            classes = enrolledClasses,
                            searchedClasses = enrolledClasses,
                            uiState =
                                if (enrolledClasses.isNotEmpty()) UiState.Success
                                else UiState.SuccessEmpty,
                        )
                    }
                }.onNoInternet { message ->
                    updateState {
                        it.copy(uiState = UiState.NoInternet(message))
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    updateState {
                        it.copy(uiState = UiState.Error(message))
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    updateState {
                        it.copy(uiState = UiState.Error(message))
                    }
                }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    searchQuery = query,
                    isSearching = true,
                )
            }
            delay(500)

            val searchedClass = state.value.classes
                .filter {
                    it.className.startsWith(query, true)
                    || it.lecturerNames.any { lecturerName ->
                        lecturerName.startsWith(query, true)
                    }
                }

            updateState {
                it.copy(
                    isSearching = false,
                    searchedClasses = searchedClass,
                )
            }
        }
    }

    companion object {

        private const val TAG = "HomeViewModel"
    }
}