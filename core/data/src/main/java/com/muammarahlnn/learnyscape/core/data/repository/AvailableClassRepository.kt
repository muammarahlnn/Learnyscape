package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassRepository, 16/11/2023 20.43 by Muammar Ahlan Abimanyu
 */
interface AvailableClassRepository {

    fun getAvailableClasses(): Flow<List<AvailableClassModel>>
}