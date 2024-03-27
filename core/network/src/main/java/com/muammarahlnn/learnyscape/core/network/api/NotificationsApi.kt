package com.muammarahlnn.learnyscape.core.network.api

import com.muammarahlnn.learnyscape.core.network.model.response.BaseResponse
import com.muammarahlnn.learnyscape.core.network.model.response.NotificationResponse
import retrofit2.http.GET

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NotificationsApi, 26/03/2024 23.07
 */
interface NotificationsApi {

    @GET("notifications/classes")
    suspend fun getNotifications(): BaseResponse<List<NotificationResponse>>
}