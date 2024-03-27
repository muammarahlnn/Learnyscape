package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toNotificationModels
import com.muammarahlnn.learnyscape.core.data.repository.NotificationsRepository
import com.muammarahlnn.learnyscape.core.model.data.NotificationModel
import com.muammarahlnn.learnyscape.core.network.datasource.NotificationsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsRepositoryImpl, 27/03/2024 01.28
 */
class NotificationsRepositoryImpl @Inject constructor(
    private val notificationsNetworkDataSource: NotificationsNetworkDataSource,
) : NotificationsRepository {

    override fun getNotifications(): Flow<List<NotificationModel>> =
        notificationsNetworkDataSource.getNotifications().map { notificationResponses ->
            notificationResponses.toNotificationModels()
        }
}