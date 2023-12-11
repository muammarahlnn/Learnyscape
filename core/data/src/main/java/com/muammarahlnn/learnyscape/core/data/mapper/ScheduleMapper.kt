package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.DayModel
import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel
import com.muammarahlnn.learnyscape.core.network.model.response.ScheduleResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleMapper, 08/12/2023 22.25
 */

fun List<ScheduleResponse>.toScheduleModels() = map {
    it.toScheduleModel()
}

fun ScheduleResponse.toScheduleModel() = ScheduleModel(
    id = id,
    className = className,
    startTime = convertTimeInMinutesToLocalTime(startTime),
    endTime = convertTimeInMinutesToLocalTime(endTime),
    day = DayModel.getDayModel(day),
)