package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.NotificationResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsNetworkDataSource, 26/03/2024 23.21
 */
interface NotificationsNetworkDataSource {

    fun getNotifications(): Flow<List<NotificationResponse>>
}