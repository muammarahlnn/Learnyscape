package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EditAnnouncementUseCase, 01/04/2024 13.14
 */
fun interface EditAnnouncementUseCase {

    operator fun invoke(
        announcementId: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>
}