package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateAnnouncementUseCase, 13/01/2024 05.35
 */
fun interface CreateAnnouncementUseCase {

    operator fun invoke(
        classId: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>
}