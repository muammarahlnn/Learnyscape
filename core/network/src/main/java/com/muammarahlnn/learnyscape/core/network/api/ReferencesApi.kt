package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ReferencesApi, 15/01/2024 17.53
 */
interface ReferencesApi {

    @Multipart
    @POST(REFERENCES_END_POINT)
    suspend fun postReference(
        @Part files: List<MultipartBody.Part>,
        @Part(ResourceClassPartKey.CLASS_ID_PART) classId: RequestBody,
        @Part(ResourceClassPartKey.NAME_PART) title: RequestBody,
        @Part(ResourceClassPartKey.DESCRIPTION_PART) description: RequestBody?,
    ): BaseResponse<String>

    companion object {

        private const val REFERENCES_END_POINT = "references"
    }
}