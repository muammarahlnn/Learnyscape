package com.muammarahlnn.learnyscape.feature.notifications

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
import com.muammarahlnn.learnyscape.core.domain.notifications.GetNotificationsUseCase
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsContract.Effect
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsContract.Event
import com.muammarahlnn.learnyscape.feature.notifications.NotificationsContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsViewModel, 27/03/2024 01.45
 */
@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
) : ViewModel(),
    ContractProvider<UiState, Event, Effect> by contract(UiState.Loading),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            Event.FetchNotifications -> fetchNotifications()
        }
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        UiState.Loading
                    }
                }.onSuccess { notifications ->
                    updateState {
                        if (notifications.isNotEmpty()) {
                            UiState.Success(notifications)
                        } else {
                            UiState.SuccessEmpty
                        }
                    }
                }.onNoInternet { message ->
                    updateState {
                        UiState.NoInternet(message)
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    updateState {
                        UiState.Error(message)
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    updateState {
                        UiState.Error(message)
                    }
                }
            }
        }
    }

    private companion object {

        const val TAG = "NotificationsViewModel"
    }
}