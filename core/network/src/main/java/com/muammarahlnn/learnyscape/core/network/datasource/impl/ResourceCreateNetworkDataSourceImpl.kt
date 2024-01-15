package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.util.convertToTextRequestBody
import com.muammarahlnn.learnyscape.core.network.util.toFileParts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateNetworkDataSourceImpl, 13/01/2024 04.35
 */
@Singleton
class ResourceCreateNetworkDataSourceImpl @Inject constructor(
    private val announcementsApi: AnnouncementsApi,
    private val referencesApi: ReferencesApi,
) : ResourceCreateNetworkDataSource {

    override fun postAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            announcementsApi.postAnnouncement(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.convertToTextRequestBody(),
                description = description.convertToTextRequestBody()
            ).data
        )
    }

    override fun postReference(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            referencesApi.postReference(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.convertToTextRequestBody(),
                title = title.convertToTextRequestBody(),
                description = if (description.isNotEmpty()) {
                    description.convertToTextRequestBody()
                } else null,
            ).data
        )
    }
}