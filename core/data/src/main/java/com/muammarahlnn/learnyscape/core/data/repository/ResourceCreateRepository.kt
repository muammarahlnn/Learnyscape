package com.muammarahlnn.learnyscape.core.data.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateRepository, 13/01/2024 05.31
 */
interface ResourceCreateRepository {

    fun createAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun createModule(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>
}