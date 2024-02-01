package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskSubmissionResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsNetworkDataSource, 18/01/2024 17.38
 */
interface ResourceDetailsNetworkDataSource {

    fun getAttachment(attachmentUrl: String): Flow<File?>

    fun getAnnouncementDetails(announcementId: String): Flow<AnnouncementDetailsResponse>

    fun deleteAnnouncement(announcementId: String): Flow<String>

    fun getReferenceDetails(referenceId: String): Flow<ReferenceDetailsResponse>

    fun deleteReference(referenceId: String): Flow<String>

    fun getTaskDetails(taskId: String): Flow<TaskDetailsResponse>

    fun getTaskSubmissions(taskId: String): Flow<List<TaskSubmissionResponse>>

    fun deleteTask(taskId: String): Flow<String>

    fun getQuizDetails(quizId: String): Flow<QuizDetailsResponse>
}