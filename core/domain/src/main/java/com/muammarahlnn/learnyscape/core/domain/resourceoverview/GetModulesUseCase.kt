package com.muammarahlnn.learnyscape.core.domain.resourceoverview

import com.muammarahlnn.learnyscape.core.model.data.ModuleOverviewModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetModulesUseCase, 17/01/2024 15.49
 */
fun interface GetModulesUseCase {

    operator fun invoke(classId: String): Flow<List<ModuleOverviewModel>>
}