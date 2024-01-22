package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceOverviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Url

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

    @GET(GET_REFERENCES_END_POINT)
    suspend fun getReferences(
        @Path(CLASS_ID_PATH) classId: String,
    ): BaseResponse<List<ReferenceOverviewResponse>>

    @GET(GET_REFERENCE_DETAILS_END_PONT)
    suspend fun getReferenceDetails(
        @Path(REFERENCE_ID_PATH) referenceId: String,
    ): BaseResponse<ReferenceDetailsResponse>

    @GET
    suspend fun getReferenceAttachment(
        @Url attachmentUrl: String,
    ): Response<ResponseBody>

    companion object {

        private const val CLASS_ID_PATH = "classId"

        private const val REFERENCE_ID_PATH = "referenceId"

        private const val REFERENCES_END_POINT = "references"

        private const val GET_REFERENCES_END_POINT = "$REFERENCES_END_POINT/classes/{$CLASS_ID_PATH}"

        private const val GET_REFERENCE_DETAILS_END_PONT = "$REFERENCES_END_POINT/{$REFERENCE_ID_PATH}"
    }
}