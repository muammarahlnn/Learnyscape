package com.muammarahlnn.learnyscape.core.domain.classmembers

import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetClassMembersUseCase, 25/01/2024 22.15
 */
fun interface GetClassMembersUseCase {

    operator fun invoke(classId: String): Flow<EnrolledClassMembersModel>
}