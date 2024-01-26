package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EnrolledClassMembersModel, 25/01/2024 20.29
 */
data class EnrolledClassMembersModel(
    val lecturers: List<ClassMember>,
    val students: List<ClassMember>,
) {

    data class ClassMember(
        val id: String,
        val name: String,
        val profilePicUrl: String,
    )
}
