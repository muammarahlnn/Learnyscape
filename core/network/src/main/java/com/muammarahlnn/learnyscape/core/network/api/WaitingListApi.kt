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

    @PUT(WAITING_LIST_STUDENT_END_POINT)
    suspend fun putStudentAcceptance(
        @Path(STUDENT_ID_PATH) studentId: String,
        @Body studentAcceptanceRequest: StudentAcceptanceRequest,
    ): BaseResponse<String>

    companion object {

        private const val WAITING_LIST_END_POINT = "waiting-lists"

        private const val STUDENT_ID_PATH = "studentId"

        private const val WAITING_LIST_STUDENT_END_POINT = "$WAITING_LIST_END_POINT/{$STUDENT_ID_PATH}"
    }
}