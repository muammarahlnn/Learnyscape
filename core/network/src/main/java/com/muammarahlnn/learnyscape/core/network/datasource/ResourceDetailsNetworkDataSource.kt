package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LecturerTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.StudentTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsNetworkDataSource, 18/01/2024 17.38
 */
interface ResourceDetailsNetworkDataSource {

    fun getAnnouncementDetails(announcementId: String): Flow<AnnouncementDetailsResponse>

    fun deleteAnnouncement(announcementId: String): Flow<String>

    fun getReferenceDetails(referenceId: String): Flow<ReferenceDetailsResponse>

    fun deleteReference(referenceId: String): Flow<String>

    fun getTaskDetails(taskId: String): Flow<TaskDetailsResponse>

    fun lecturerGetTaskSubmissions(taskId: String): Flow<List<LecturerTaskSubmissionResponse>>

    fun studentGetTaskSubmission(taskId: String): Flow<StudentTaskSubmissionResponse>

    fun deleteTask(taskId: String): Flow<String>

    fun getQuizDetails(quizId: String): Flow<QuizDetailsResponse>

    fun getQuizSubmissions(quizId: String): Flow<List<QuizSubmissionResponse>>

    fun uploadTaskSubmission(
        taskId: String,
        attachments: List<File>,
    ): Flow<String>

    fun updateTaskSubmission(
        submissionId: String,
        attachments: List<File>,
    ): Flow<String>

    fun turnInTaskSubmission(
        submissionId: String,
        turnIn: Boolean,
    ): Flow<String>

    fun isQuizTaken(quizId: String): Flow<Boolean>

    fun deleteQuiz(quizId: String): Flow<String>
}