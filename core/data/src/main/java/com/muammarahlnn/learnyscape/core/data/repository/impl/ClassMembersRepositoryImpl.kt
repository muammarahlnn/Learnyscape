package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toEnrolledClassMembersModel
import com.muammarahlnn.learnyscape.core.data.repository.ClassMembersRepository
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel
import com.muammarahlnn.learnyscape.core.network.datasource.ClassMembersNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersRepositoryImpl, 25/01/2024 20.24
 */
class ClassMembersRepositoryImpl @Inject constructor(
    private val classMembersNetworkDataSource: ClassMembersNetworkDataSource,
) : ClassMembersRepository {

    override fun getClassMembers(classId: String): Flow<EnrolledClassMembersModel> =
        classMembersNetworkDataSource.getClassMembers(classId).map {
            it.toEnrolledClassMembersModel()
        }
}