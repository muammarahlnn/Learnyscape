package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateModuleUseCase, 15/01/2024 18.29
 */
fun interface CreateModuleUseCase {

    operator fun invoke(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>
}