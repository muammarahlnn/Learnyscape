package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.ClassInfoResponse
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import retrofit2.http.GET


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeApi, 12/10/2023 22.02 by Muammar Ahlan Abimanyu
 */
interface HomeApi {

    @GET(GET_CLASSES_END_POINT)
    suspend fun getClasses(): BaseResponse<List<ClassInfoResponse>>

    companion object {

        private const val GET_CLASSES_END_POINT = "users/classes"
    }
}