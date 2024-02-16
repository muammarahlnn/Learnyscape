package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsRepository, 13/02/2024 19.41
 */
interface SubmissionDetailsRepository {

    fun getAssignmentSubmissionDetails(submissionId: String): Flow<AssignmentSubmissionModel>

    fun getStudentQuizAnswers(quizId: String, studentId: String): Flow<List<StudentQuizAnswerModel>>
}