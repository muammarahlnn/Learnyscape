package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.NotificationModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsRepository, 26/03/2024 23.24
 */
interface NotificationsRepository {

    fun getNotifications(): Flow<List<NotificationModel>>
}