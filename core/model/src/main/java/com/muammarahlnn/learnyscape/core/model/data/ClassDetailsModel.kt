package com.muammarahlnn.learnyscape.core.model.data

import kotlinx.datetime.LocalTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassDetailsModel, 25/02/2024 09.57
 */
data class ClassDetailsModel(
    val id: String,
    val name: String,
    val day: DayModel,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val lecturers: List<ClassMemberModel>,
)