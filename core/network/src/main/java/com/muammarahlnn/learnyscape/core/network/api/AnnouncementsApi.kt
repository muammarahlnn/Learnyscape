package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementApi, 13/01/2024 03.19
 */
interface AnnouncementsApi {

    @Multipart
    @POST("announcements")
    suspend fun postAnnouncement(
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.CLASS_ID_PART) classId: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody,
    ): BaseResponse<String>

    @GET("announcements/{classId}")
    suspend fun getAnnouncements(
        @Path("classId") classId: String
    ): BaseResponse<List<AnnouncementOverviewResponse>>

    @GET("announcements/{announcementId}")
    suspend fun getAnnouncementDetails(
        @Path("announcementId") announcementId: String,
    ): BaseResponse<AnnouncementDetailsResponse>

    @DELETE("announcements/{announcementId}")
    suspend fun deleteAnnouncement(
        @Path("announcementId") announcementId: String,
    ): BaseResponse<String>

    @Multipart
    @PUT("announcements/{announcementId}")
    suspend fun putAnnouncement(
        @Path("announcementId") announcementId: String,
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody,
    ): BaseResponse<String>
}