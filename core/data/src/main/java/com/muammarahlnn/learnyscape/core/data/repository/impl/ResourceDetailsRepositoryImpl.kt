package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toAnnouncementDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentSubmissionModels
import com.muammarahlnn.learnyscape.core.data.mapper.toModuleDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toQuizDetailsModel
import com.muammarahlnn.learnyscape.core.data.mapper.toQuizSubmissionModels
import com.muammarahlnn.learnyscape.core.data.repository.ResourceDetailsRepository
import com.muammarahlnn.learnyscape.core.model.data.AnnouncementDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.QuizDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.network.datasource.AttachmentNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsRepositoryImpl, 18/01/2024 17.43
 */
class ResourceDetailsRepositoryImpl @Inject constructor(
    private val resourceDetailsNetworkDataSource: ResourceDetailsNetworkDataSource,
    private val attachmentNetworkDataSource: AttachmentNetworkDataSource,
) : ResourceDetailsRepository {

    override fun getAnnouncementDetails(announcementId: String): Flow<AnnouncementDetailsModel> =
        resourceDetailsNetworkDataSource.getAnnouncementDetails(announcementId).flatMapLatest { announcementDetailsResponse ->
            getAttachments(announcementDetailsResponse.attachmentUrls).map { attachmentResponses ->
                announcementDetailsResponse.toAnnouncementDetailsModel(attachmentResponses)
            }
        }

    override fun deleteAnnouncement(announcementId: String): Flow<String> =
        resourceDetailsNetworkDataSource.deleteAnnouncement(announcementId)

    override fun getModuleDetails(moduleId: String): Flow<ModuleDetailsModel> =
        resourceDetailsNetworkDataSource.getReferenceDetails(moduleId).flatMapLatest { referenceDetailsResponse ->
            getAttachments(referenceDetailsResponse.attachmentUrls).map { attachmentResponses ->
                referenceDetailsResponse.toModuleDetailsModel(attachmentResponses)
            }
        }

    override fun deleteModule(moduleId: String): Flow<String> =
        resourceDetailsNetworkDataSource.deleteReference(moduleId)

    override fun getAssignmentDetails(assignmentId: String): Flow<AssignmentDetailsModel> =
        resourceDetailsNetworkDataSource.getTaskDetails(assignmentId).flatMapLatest { taskDetailsResponse ->
            getAttachments(taskDetailsResponse.attachmentUrls).map { attachmentResponses ->
                taskDetailsResponse.toAssignmentDetailsModel(attachmentResponses)
            }
        }

    override fun lecturerGetAssignmentSubmissions(assignmentId: String): Flow<List<StudentSubmissionModel>> =
        resourceDetailsNetworkDataSource.lecturerGetTaskSubmissions(assignmentId).map { lecturerTaskSubmissionResponses ->
            lecturerTaskSubmissionResponses.toAssignmentSubmissionModels()
        }

    override fun studentGetAssignmentSubmission(assignmentId: String): Flow<AssignmentSubmissionModel> =
        resourceDetailsNetworkDataSource.studentGetTaskSubmission(assignmentId).flatMapLatest { studentTaskSubmissionResponse ->
            getAttachments(studentTaskSubmissionResponse.attachmentUrls).map { attachmentResponses ->
                studentTaskSubmissionResponse.toAssignmentSubmissionModel(attachmentResponses)
            }
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


    override fun isQuizTaken(quizId: String): Flow<Boolean> =
        resourceDetailsNetworkDataSource.isQuizTaken(quizId)

    private fun getAttachments(attachmentUrls: List<String>): Flow<List<File>> =
        attachmentNetworkDataSource.getAttachments(attachmentUrls).map { it.filterNotNull() }
}