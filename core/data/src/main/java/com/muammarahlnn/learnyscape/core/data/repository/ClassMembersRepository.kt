package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersRepository, 25/01/2024 20.24
 */
interface ClassMembersRepository {

    fun getClassMembers(classId: String): Flow<EnrolledClassMembersModel>
}