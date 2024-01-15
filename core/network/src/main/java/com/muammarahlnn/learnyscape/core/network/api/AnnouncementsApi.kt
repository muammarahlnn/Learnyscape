package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementApi, 13/01/2024 03.19
 */
interface AnnouncementsApi {

    @Multipart
    @POST(ANNOUNCEMENTS_END_POINT)
    suspend fun postAnnouncement(
        @Part files: List<MultipartBody.Part>,
        @Part(CLASS_ID_PART) classId: RequestBody,
        @Part(DESCRIPTION_PART) description: RequestBody,
    ): BaseResponse<String>

    companion object {

        private const val ANNOUNCEMENTS_END_POINT = "announcements"

        private const val FILES_PART = "files"

        private const val CLASS_ID_PART = "classId"

        private const val DESCRIPTION_PART = "description"
    }
}