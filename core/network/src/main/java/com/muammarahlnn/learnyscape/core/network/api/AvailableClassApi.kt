package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.AvailableClassResponse
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import retrofit2.http.GET


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassApi, 16/11/2023 19.50 by Muammar Ahlan Abimanyu
 */
interface AvailableClassApi {

    @GET(GET_AVAILABLE_CLASSES_END_POINT)
    suspend fun getAvailableClasses(): BaseResponse<List<AvailableClassResponse>>

    companion object {

        private const val GET_AVAILABLE_CLASSES_END_POINT = "classes"
    }
}