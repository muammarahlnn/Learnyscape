package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.data.util.formatIsoDate
import com.muammarahlnn.learnyscape.core.model.data.NotificationModel
import com.muammarahlnn.learnyscape.core.network.model.response.NotificationResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsMapper, 27/03/2024 01.29
 */
fun List<NotificationResponse>.toNotificationModels() = map {
    it.toNotificationModel()
}

fun NotificationResponse.toNotificationModel() = NotificationModel(
    id = id,
    createdAt = formatIsoDate(createdAt),
    type = type.toClassFeedTypeModel(),
    uri = uri,
    description = description,
    title = title,
)