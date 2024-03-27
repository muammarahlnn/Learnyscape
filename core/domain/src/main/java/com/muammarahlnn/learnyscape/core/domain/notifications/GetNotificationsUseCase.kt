package com.muammarahlnn.learnyscape.core.domain.notifications

import com.muammarahlnn.learnyscape.core.model.data.NotificationModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetNotificationsUseCase, 27/03/2024 01.33
 */
fun interface GetNotificationsUseCase : () -> Flow<List<NotificationModel>>