package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementApi, 13/01/2024 03.19
 */
interface AnnouncementsApi {

    @Multipart
    @POST(ANNOUNCEMENTS_END_POINT)
    suspend fun postAnnouncement(
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.CLASS_ID_PART) classId: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody,
    ): BaseResponse<String>

    @GET(GET_ANNOUNCEMENTS_END_POINT)
    suspend fun getAnnouncements(
        @Path(CLASS_ID_PATH) classId: String
    ): BaseResponse<List<AnnouncementOverviewResponse>>

    companion object {

        private const val ANNOUNCEMENTS_END_POINT = "announcements"

        private const val CLASS_ID_PATH = "classId"

        private const val GET_ANNOUNCEMENTS_END_POINT = "$ANNOUNCEMENTS_END_POINT/{$CLASS_ID_PATH}"
    }
}