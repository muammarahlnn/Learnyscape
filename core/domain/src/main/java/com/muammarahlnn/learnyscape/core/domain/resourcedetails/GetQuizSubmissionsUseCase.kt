package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetQuizSubmissionsUseCase, 01/02/2024 15.22
 */
fun interface GetQuizSubmissionsUseCase {

    operator fun invoke(quizId: String): Flow<List<StudentSubmissionModel>>
}