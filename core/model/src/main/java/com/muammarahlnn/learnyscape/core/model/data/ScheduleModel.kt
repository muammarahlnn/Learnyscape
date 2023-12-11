package com.muammarahlnn.learnyscape.core.model.data

import kotlinx.datetime.LocalTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleModel, 08/12/2023 22.23
 */
data class ScheduleModel(
    val id: String,
    val className: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val day: DayModel,
)