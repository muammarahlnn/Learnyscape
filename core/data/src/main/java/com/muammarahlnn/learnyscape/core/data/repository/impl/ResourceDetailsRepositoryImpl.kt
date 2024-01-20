package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toModuleDetailsModel
import com.muammarahlnn.learnyscape.core.data.repository.ResourceDetailsRepository
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsRepositoryImpl, 18/01/2024 17.43
 */
class ResourceDetailsRepositoryImpl @Inject constructor(
    private val resourceDetailsNetworkDataSource: ResourceDetailsNetworkDataSource,
) : ResourceDetailsRepository {

    override fun getModuleDetails(moduleId: String): Flow<ModuleDetailsModel> =
        resourceDetailsNetworkDataSource.getReferenceDetails(referenceId = moduleId).map {
            it.toModuleDetailsModel()
        }
}