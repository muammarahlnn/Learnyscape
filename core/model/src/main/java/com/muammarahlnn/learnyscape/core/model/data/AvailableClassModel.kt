package com.muammarahlnn.learnyscape.core.model.data

import kotlinx.datetime.LocalTime


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassModel, 16/11/2023 20.08 by Muammar Ahlan Abimanyu
 */
data class AvailableClassModel(
    val id: String,
    val name: String,
    val day: DayModel,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val lecturers: List<ClassMemberModel>,
    val students: List<ClassMemberModel>,
)

fun convertTimeInMinutesToLocalTime(time: Int): LocalTime {
    val hour = time / 60
    val minute = time % 60
    return LocalTime(
        hour = hour,
        minute = minute,
    )
}