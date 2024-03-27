package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.AnnouncementDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.QuizDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsRepository, 18/01/2024 17.40
 */
interface ResourceDetailsRepository {

    fun getAnnouncementDetails(announcementId: String): Flow<AnnouncementDetailsModel>

    fun deleteAnnouncement(announcementId: String): Flow<String>

    fun getModuleDetails(moduleId: String): Flow<ModuleDetailsModel>

    fun deleteModule(moduleId: String): Flow<String>

    fun getAssignmentDetails(assignmentId: String): Flow<AssignmentDetailsModel>

    fun lecturerGetAssignmentSubmissions(assignmentId: String): Flow<List<StudentSubmissionModel>>

    fun studentGetAssignmentSubmission(assignmentId: String): Flow<AssignmentSubmissionModel>

    fun deleteAssignment(assignmentId: String): Flow<String>

    fun getQuizDetails(quizId: String): Flow<QuizDetailsModel>

    fun getQuizSubmissions(quizId: String): Flow<List<StudentSubmissionModel>>

    fun uploadAssignmentAttachments(
        assignmentId: String,
        attachments: List<File>,
    ): Flow<String>

    fun updateAssignmentAttachments(
        submissionId: String,
        attachments: List<File>,
    ): Flow<String>

    fun turnInAssignmentSubmission(
        submissionId: String,
        turnIn: Boolean,
    ): Flow<String>

    fun isQuizTaken(quizId: String): Flow<Boolean>

    fun deleteQuiz(quizId: String): Flow<String>
}