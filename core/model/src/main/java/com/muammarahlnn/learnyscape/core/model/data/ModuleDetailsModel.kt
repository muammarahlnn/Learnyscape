package com.muammarahlnn.learnyscape.core.model.data

import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ModuleDetailsModel, 18/01/2024 17.41
 */
data class ModuleDetailsModel(
    val id: String,
    val name: String,
    val description: String,
    val updatedAt: String,
    val attachments: List<File>,
)