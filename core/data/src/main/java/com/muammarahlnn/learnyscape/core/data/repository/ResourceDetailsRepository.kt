package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsRepository, 18/01/2024 17.40
 */
interface ResourceDetailsRepository {

    fun getModuleDetails(moduleId: String): Flow<ModuleDetailsModel>

    fun deleteModule(moduleId: String): Flow<String>
}