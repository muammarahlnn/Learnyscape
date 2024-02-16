package com.muammarahlnn.learnyscape.core.domain.submissiondetails

import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetStudentQuizAnswersUseCase, 16/02/2024 12.09
 */
fun interface GetStudentQuizAnswersUseCase {

    operator fun invoke(
        quizId: String,
        studentId: String,
    ): Flow<List<StudentQuizAnswerModel>>
}