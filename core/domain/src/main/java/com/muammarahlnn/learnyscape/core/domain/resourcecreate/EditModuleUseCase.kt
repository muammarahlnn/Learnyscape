package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EditModuleUseCase, 01/04/2024 13.14
 */
fun interface EditModuleUseCase {

    operator fun invoke(
        moduleId: String,
        title: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>
}