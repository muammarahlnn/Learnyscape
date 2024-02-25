package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.AvailableClassResponse
import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ClassDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ClassFeedResponse
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassApi, 16/11/2023 19.50 by Muammar Ahlan Abimanyu
 */
interface ClassesApi {

    @GET("classes")
    suspend fun getAvailableClasses(): BaseResponse<List<AvailableClassResponse>>

    @GET("classes/{classId}/histories")
    suspend fun getClassHistories(
        @Path("classId") classId: String,
    ): BaseResponse<List<ClassFeedResponse>>

    @GET("classes/{classId}")
    suspend fun getClassDetails(
        @Path("classId") classId: String,
    ): BaseResponse<ClassDetailsResponse>
}