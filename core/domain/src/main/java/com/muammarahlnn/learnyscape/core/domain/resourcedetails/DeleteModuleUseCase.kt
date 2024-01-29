package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DeleteModuleUseCase, 28/01/2024 15.24
 */
fun interface DeleteModuleUseCase {

    operator fun invoke(moduleId: String): Flow<String>
}