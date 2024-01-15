package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.repository.ResourceCreateRepository
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateRepositoryImpl, 13/01/2024 05.32
 */
class ResourceCreateRepositoryImpl @Inject constructor(
    private val resourceCreateNetworkDataSource: ResourceCreateNetworkDataSource,
) : ResourceCreateRepository {

    override fun createAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.postAnnouncement(
        classId = classId,
        description = description,
        attachments = attachments,
    )
}