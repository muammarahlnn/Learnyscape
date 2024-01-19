package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetModuleDetailsUseCase, 18/01/2024 17.49
 */
fun interface GetModuleDetailsUseCase {

    operator fun invoke(moduleId: String): Flow<ModuleDetailsModel>
}