package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.repository.ResourceCreateRepository
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateRepositoryImpl, 13/01/2024 05.32
 */
class ResourceCreateRepositoryImpl @Inject constructor(
    private val resourceCreateNetworkDataSource: ResourceCreateNetworkDataSource,
) : ResourceCreateRepository {

    override fun createAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.postAnnouncement(
        classId = classId,
        description = description,
        attachments = attachments,
    )

    override fun createModule(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.postReference(
        classId = classId,
        title = title,
        description = description,
        attachments = attachments,
    )

    override fun createAssignment(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.postTask(
        classId = classId,
        title = title,
        description = description,
        dueDate = dueDate,
        attachments = attachments,
    )

    override fun createQuiz(
        classId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int,
    ): Flow<String> = resourceCreateNetworkDataSource.postQuiz(
        classId = classId,
        title = title,
        description = description,
        quizType = quizType,
        startDate = startDate,
        endDate = endDate,
        duration = duration,
    )

    override fun addQuizQuestions(
        quizId: String,
        questions: List<MultipleChoiceQuestionModel>
    ): Flow<String> = resourceCreateNetworkDataSource.postQuizProblems(
        quizId = quizId,
        multipleChoiceQuestions = questions,
    )

    override fun editAnnouncement(
        announcementId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.putAnnouncement(
        announcementId = announcementId,
        description = description,
        attachments = attachments,
    )

    override fun editModule(
        moduleId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.putReference(
        referenceId = moduleId,
        title = title,
        description = description,
        attachments = attachments,
    )

    override fun editAssignment(
        assignmentId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = resourceCreateNetworkDataSource.putTask(
        taskId = assignmentId,
        title = title,
        description = description,
        dueDate = dueDate,
        attachments = attachments,
    )

    override fun editQuiz(
        quizId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int
    ): Flow<String> = resourceCreateNetworkDataSource.putQuiz(
        quizId = quizId,
        title = title,
        description = description,
        quizType = quizType,
        startDate = startDate,
        endDate = endDate,
        duration = duration,
    )
}