package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TodayScheduleResponse, 08/12/2023 22.15
 */
@Serializable
data class ScheduleResponse(
    val id: String,
    val className: String,
    val startTime: Int,
    val endTime: Int,
    val day: String,
)