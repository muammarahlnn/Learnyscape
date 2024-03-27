package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationModel, 26/03/2024 23.25
 */
data class NotificationModel(
    val id: String,
    val createdAt: String,
    val type: ClassFeedTypeModel,
    val uri: String,
    val title: String,
    val description: String,
)