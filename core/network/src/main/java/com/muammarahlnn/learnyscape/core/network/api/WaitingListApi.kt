package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.request.StudentAcceptanceRequest
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * @Author Muammar Ahlan Abimanyu
 * @File WaitingListApi, 18/01/2024 12.01
 */
interface WaitingListApi {

    @PUT("waiting-lists/{studentId}")
    suspend fun putStudentAcceptance(
        @Path("studentId") studentId: String,
        @Body studentAcceptanceRequest: StudentAcceptanceRequest,
    ): BaseResponse<String>

    suspend fun getStudentPendingRequestClasses()
}