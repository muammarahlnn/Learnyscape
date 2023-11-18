package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.AvailableClassModel
import com.muammarahlnn.learnyscape.core.model.data.ClassMemberModel
import com.muammarahlnn.learnyscape.core.model.data.DayModel
import com.muammarahlnn.learnyscape.core.model.data.convertTimeInMinutesToLocalTime
import com.muammarahlnn.learnyscape.core.network.model.response.AvailableClassResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ClassMemberResponse


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassMapper, 16/11/2023 20.14 by Muammar Ahlan Abimanyu
 */
fun List<AvailableClassResponse>.toAvailableClassModels() = map {
    it.toAvailableClassModel()
}

fun AvailableClassResponse.toAvailableClassModel() = AvailableClassModel(
    id = id,
    name = name,
    day = DayModel.getDayModel(day),
    startTime = convertTimeInMinutesToLocalTime(time),
    endTime = convertTimeInMinutesToLocalTime(endTime),
    lecturers = lecturers.map { it.toClassMemberModel() },
    students = students.map { it.toClassMemberModel() }
)

fun ClassMemberResponse.toClassMemberModel() = ClassMemberModel(
    id = id,
    fullName = fullName,
    username = username,
)

