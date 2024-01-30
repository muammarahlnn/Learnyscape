package com.muammarahlnn.learnyscape.core.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AttachmentApi, 29/01/2024 21.14
 */
interface AttachmentApi {

    @GET
    suspend fun getAttachment(
        @Url attachmentUrl: String,
    ): Response<ResponseBody>
}