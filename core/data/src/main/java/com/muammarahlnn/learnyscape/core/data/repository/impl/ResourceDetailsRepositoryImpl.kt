package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toAnnouncementDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentSubmissionModels
import com.muammarahlnn.learnyscape.core.data.mapper.toModuleDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toQuizDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toQuizSubmissionModels
import com.muammarahlnn.learnyscape.core.data.repository.ResourceDetailsRepository
import com.muammarahlnn.learnyscape.core.model.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.AnnouncementDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.QuizDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsRepositoryImpl, 18/01/2024 17.43
 */
class ResourceDetailsRepositoryImpl @Inject constructor(
    private val resourceDetailsNetworkDataSource: ResourceDetailsNetworkDataSource,
) : ResourceDetailsRepository {

    override fun getAnnouncementDetails(announcementId: String): Flow<AnnouncementDetailsModel> =
        resourceDetailsNetworkDataSource.getAnnouncementDetails(announcementId).map { announcementDetailsResponse ->
            announcementDetailsResponse.toAnnouncementDetailsModel(
                getAttachments(announcementDetailsResponse.attachmentUrls)
            )
        }

    override fun deleteAnnouncement(announcementId: String): Flow<String> =
        resourceDetailsNetworkDataSource.deleteAnnouncement(announcementId)

    override fun getModuleDetails(moduleId: String): Flow<ModuleDetailsModel> =
        resourceDetailsNetworkDataSource.getReferenceDetails(moduleId).map { referenceDetailsResponse ->
            referenceDetailsResponse.toModuleDetailsModel(
                getAttachments(referenceDetailsResponse.attachmentUrls)
            )
        }

    override fun deleteModule(moduleId: String): Flow<String> =
        resourceDetailsNetworkDataSource.deleteReference(moduleId)

    override fun getAssignmentDetails(assignmentId: String): Flow<AssignmentDetailsModel> =
        resourceDetailsNetworkDataSource.getTaskDetails(assignmentId).map { taskDetailsResponse ->
            taskDetailsResponse.toAssignmentDetailsModel(
                getAttachments(taskDetailsResponse.attachmentUrls)
            )
        }

    override fun lecturerGetAssignmentSubmissions(assignmentId: String): Flow<List<StudentSubmissionModel>> =
        resourceDetailsNetworkDataSource.lecturerGetTaskSubmissions(assignmentId).map { lecturerTaskSubmissionResponses ->
            lecturerTaskSubmissionResponses.toAssignmentSubmissionModels()
        }

    override fun studentGetAssignmentSubmission(assignmentId: String): Flow<AssignmentSubmissionModel> =
        resourceDetailsNetworkDataSource.studentGetTaskSubmission(assignmentId).map { studentTaskSubmissionResponse ->
            studentTaskSubmissionResponse.toAssignmentSubmissionModel(
                getAttachments(studentTaskSubmissionResponse.attachmentUrls)
            )
        }

    override fun deleteAssignment(assignmentId: String): Flow<String> =
        resourceDetailsNetworkDataSource.deleteTask(assignmentId)

    override fun getQuizDetails(quizId: String): Flow<QuizDetailsModel> =
        resourceDetailsNetworkDataSource.getQuizDetails(quizId).map { quizDetailsResponse ->
            quizDetailsResponse.toQuizDetailsModel()
        }

    override fun getQuizSubmissions(quizId: String): Flow<List<StudentSubmissionModel>> =
        resourceDetailsNetworkDataSource.getQuizSubmissions(quizId).map { quizSubmissionResponses ->
            quizSubmissionResponses.toQuizSubmissionModels()
        }

    override fun uploadAssignmentAttachments(assignmentId: String, attachments: List<File>): Flow<String> =
        resourceDetailsNetworkDataSource.uploadTaskSubmission(assignmentId, attachments)

    override fun updateAssignmentAttachments(
        submissionId: String,
        attachments: List<File>
    ): Flow<String> = resourceDetailsNetworkDataSource.updateTaskSubmission(submissionId, attachments)

    override fun turnInAssignmentSubmission(submissionId: String, turnIn: Boolean): Flow<String> =
        resourceDetailsNetworkDataSource.turnInTaskSubmission(submissionId, turnIn)

    private suspend fun getAttachments(attachmentUrls: List<String>): List<File> {
        val attachments = mutableListOf<File>()
        attachmentUrls.forEach { attachmentUrl ->
            resourceDetailsNetworkDataSource.getAttachment(attachmentUrl).collect { attachment ->
                attachment?.let {
                    attachments.add(it)
                }
            }
        }

        return attachments.toList()
    }
}