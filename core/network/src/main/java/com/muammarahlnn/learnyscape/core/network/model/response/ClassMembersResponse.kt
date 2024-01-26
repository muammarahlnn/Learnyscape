package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersResponse, 25/01/2024 20.14
 */
@Serializable
data class ClassMembersResponse(
    val classId: String,
    val className: String,
    val lecturers: List<Lecturer>,
    val students: List<Student>,
) {

    @Serializable
    data class Lecturer(
        val name: String,
        val lecturerId: String,
        @SerialName("profileURI") val profileUri: String,
    )

    @Serializable
    data class Student(
        val name: String,
        val studentId: String,
        @SerialName("profileURI") val profileUri: String,
    )
}