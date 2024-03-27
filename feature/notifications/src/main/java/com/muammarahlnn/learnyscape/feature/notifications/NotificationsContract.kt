package com.muammarahlnn.learnyscape.feature.notifications

import com.muammarahlnn.learnyscape.core.model.data.NotificationModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsContract, 27/03/2024 01.37
 */
interface NotificationsContract {

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val notifications: List<NotificationModel>) : UiState

        data object SuccessEmpty : UiState

        data class NoInternet(val message: String) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data object FetchNotifications : Event
    }

    sealed interface Effect
}