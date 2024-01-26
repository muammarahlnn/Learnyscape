package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel
import com.muammarahlnn.learnyscape.core.network.model.response.ClassMembersResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersMapper, 25/01/2024 20.30
 */
fun ClassMembersResponse.toEnrolledClassMembersModel() = EnrolledClassMembersModel(
    lecturers = lecturers.map {
        EnrolledClassMembersModel.ClassMember(
            id = it.lecturerId,
            name = it.name,
            profilePicUrl = it.profileUri,
        )
    },
    students = students.map {
        EnrolledClassMembersModel.ClassMember(
            id = it.studentId,
            name = it.name,
            profilePicUrl = it.profileUri,
        )
    }
)