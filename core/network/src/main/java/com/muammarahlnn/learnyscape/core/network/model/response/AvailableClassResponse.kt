package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassResponse, 16/11/2023 19.52 by Muammar Ahlan Abimanyu
 */
@Serializable
data class AvailableClassResponse(
    val id: String,
    val name: String,
    val day: String,
    val time: Int,
    val endTime: Int,
    val lecturers: List<ClassMemberResponse>,
    val students: List<ClassMemberResponse>,
)