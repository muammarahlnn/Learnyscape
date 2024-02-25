package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassDetailsResponse, 24/02/2024 23.57
 */
@Serializable
data class ClassDetailsResponse(
    val id: String,
    val name: String,
    val day: Day,
    val time: Int,
    val endTime: Int,
    val lecturers: List<ClassMemberResponse>,
) {
    enum class Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,
    }
}