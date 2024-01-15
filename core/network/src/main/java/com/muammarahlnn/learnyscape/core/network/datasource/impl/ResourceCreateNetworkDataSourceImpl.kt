package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.util.convertToTextRequestBody
import com.muammarahlnn.learnyscape.core.network.util.createFormData
import com.muammarahlnn.learnyscape.core.network.util.getMimeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
) : ResourceCreateNetworkDataSource {

    override fun postAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> {
        val classIdBody = classId.convertToTextRequestBody()
        val descriptionBody = description.convertToTextRequestBody()

        val filesParts = mutableListOf<MultipartBody.Part>()
        attachments.forEach { attachment ->
            val fileBody = attachment.asRequestBody(attachment.getMimeType().toMediaTypeOrNull())
            filesParts.add(
                createFormData(
                    partName = "files",
                    fileName = attachment.name,
                    requestBody = fileBody
                )
            )
        }

        return flow {
            emit(
                announcementsApi.postAnnouncement(
                    files = filesParts,
                    classId = classIdBody,
                    description = descriptionBody,
                ).data
            )
        }
    }
}