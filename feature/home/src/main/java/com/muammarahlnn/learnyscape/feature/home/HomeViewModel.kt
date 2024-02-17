package com.muammarahlnn.learnyscape.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.contract.navigation
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
import com.muammarahlnn.learnyscape.feature.home.HomeContract.Navigation
import com.muammarahlnn.learnyscape.feature.home.HomeContract.State
import com.muammarahlnn.learnyscape.feature.home.HomeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    NavigationProvider<Navigation> by navigation(),
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
                            uiState = if (enrolledClasses.isNotEmpty()) {
                                UiState.Success(enrolledClasses)
                            } else {
                                UiState.SuccessEmptyClasses
                            }
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
        updateState {
            it.copy(searchQuery = query)
        }
    }

    companion object {

        private const val TAG = "HomeViewModel"
    }
}