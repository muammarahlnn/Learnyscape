package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.NotificationsApi
import com.muammarahlnn.learnyscape.core.network.datasource.NotificationsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.NotificationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsNetworkDataSourceImpl, 26/03/2024 23.22
 */
@Singleton
class NotificationsNetworkDataSourceImpl @Inject constructor(
    private val notificationsApi: NotificationsApi,
) : NotificationsNetworkDataSource {

    override fun getNotifications(): Flow<List<NotificationResponse>> = flow {
        emit(notificationsApi.getNotifications().data)
    }
}