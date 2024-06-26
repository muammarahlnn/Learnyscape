package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceOverviewResponse
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
 * @File ReferencesApi, 15/01/2024 17.53
 */
interface ReferencesApi {

    @Multipart
    @POST("references")
    suspend fun postReference(
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.CLASS_ID_PART) classId: RequestBody,
        @Part(ResourceClassPartKey.NAME_PART) title: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody?,
    ): BaseResponse<String>

    @GET("references/classes/{classId}")
    suspend fun getReferences(
        @Path("classId") classId: String,
    ): BaseResponse<List<ReferenceOverviewResponse>>

    @GET("references/{referenceId}")
    suspend fun getReferenceDetails(
        @Path("referenceId") referenceId: String,
    ): BaseResponse<ReferenceDetailsResponse>

    @DELETE("references/{referenceId}")
    suspend fun deleteReference(
        @Path("referenceId") referenceId: String,
    ): BaseResponse<String>

    @Multipart
    @PUT("references/{referenceId}")
    suspend fun putReference(
        @Path("referenceId") referenceId: String,
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.NAME_PART) title: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody?,
    ): BaseResponse<String>
}