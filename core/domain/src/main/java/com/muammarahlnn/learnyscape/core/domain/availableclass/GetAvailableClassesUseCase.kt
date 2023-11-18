package com.muammarahlnn.learnyscape.core.domain.availableclass

import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetAvailableClassesUseCase, 16/11/2023 20.50 by Muammar Ahlan Abimanyu
 */
fun interface GetAvailableClassesUseCase : () -> Flow<List<AvailableClassModel>>