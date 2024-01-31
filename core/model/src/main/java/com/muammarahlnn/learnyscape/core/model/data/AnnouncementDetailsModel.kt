package com.muammarahlnn.learnyscape.core.model.data

import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementDetailsModel, 31/01/2024 16.12
 */
data class AnnouncementDetailsModel(
    val id: String,
    val authorName: String,
    val updatedAt: String,
    val description: String,
    val attachments: List<File>,
)