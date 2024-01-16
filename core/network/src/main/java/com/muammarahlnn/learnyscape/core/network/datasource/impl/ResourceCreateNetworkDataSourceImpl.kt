package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.util.toFileParts
import com.muammarahlnn.learnyscape.core.network.util.toTextRequestBody
import com.muammarahlnn.learnyscape.core.network.util.toTextRequestBodyOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
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
    private val tasksApi: TasksApi,
) : ResourceCreateNetworkDataSource {

    override fun postAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            announcementsApi.postAnnouncement(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.toTextRequestBody(),
                description = description.toTextRequestBody()
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
                classId = classId.toTextRequestBody(),
                title = title.toTextRequestBody(),
                description = description.toTextRequestBodyOrNull(),
            ).data
        )
    }

    override fun postTask(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            tasksApi.postTask(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.toTextRequestBody(),
                title = title.toTextRequestBody(),
                description = description.toTextRequestBodyOrNull(),
                dueDate = dueDate.toEpochSecond().toString().toTextRequestBody(),
            ).data
        )
    }

    private fun LocalDateTime.toEpochSecond(): Long =
        this.atZone(ZoneId.systemDefault()).toEpochSecond()
}