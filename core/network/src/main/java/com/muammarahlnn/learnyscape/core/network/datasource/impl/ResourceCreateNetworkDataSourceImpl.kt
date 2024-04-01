package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.api.QuizzesApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.request.AddQuizQuestionsRequest
import com.muammarahlnn.learnyscape.core.network.model.request.CreateQuizRequest
import com.muammarahlnn.learnyscape.core.network.model.request.EditQuizRequest
import com.muammarahlnn.learnyscape.core.network.util.toFileParts
import com.muammarahlnn.learnyscape.core.network.util.toTextRequestBody
import com.muammarahlnn.learnyscape.core.network.util.toTextRequestBodyOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateNetworkDataSourceImpl, 13/01/2024 04.35
 */
@Singleton
class ResourceCreateNetworkDataSourceImpl @Inject constructor(
    private val announcementsApi: AnnouncementsApi,
    private val referencesApi: ReferencesApi,
    private val tasksApi: TasksApi,
    private val quizzesApi: QuizzesApi,
) : ResourceCreateNetworkDataSource {

    override fun postAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            announcementsApi.postAnnouncement(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.toTextRequestBody(),
                description = description.toTextRequestBody()
            ).data
        )
    }

    override fun postReference(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            referencesApi.postReference(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.toTextRequestBody(),
                title = title.toTextRequestBody(),
                description = description.toTextRequestBodyOrNull(),
            ).data
        )
    }

    override fun postTask(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            tasksApi.postTask(
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                classId = classId.toTextRequestBody(),
                title = title.toTextRequestBody(),
                description = description.toTextRequestBodyOrNull(),
                dueDate = dueDate.toEpochSecond().toString().toTextRequestBody(),
            ).data
        )
    }

    override fun postQuiz(
        classId: String,
        title: String,
        description: String,
        quizType: String,
        duration: Int,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<String> = flow {
        val createQuizRequest = CreateQuizRequest(
            classId = classId,
            name = title,
            description = description.ifEmpty { null },
            quizType = quizType,
            duration= duration,
            startDate = startDate.toEpochSecond(),
            endDate = endDate.toEpochSecond()
        )
        emit(quizzesApi.postQuiz(createQuizRequest).data.id)
    }

    override fun postQuizProblems(
        quizId: String,
        multipleChoiceQuestions: List<MultipleChoiceQuestionModel>
    ): Flow<String> = flow {
        emit(
            quizzesApi.postQuizProblems(
                quizId = quizId,
                addProblemsQuizRequest = AddQuizQuestionsRequest(
                    problems = multipleChoiceQuestions.map {
                        AddQuizQuestionsRequest.Problem(
                            description = it.question,
                            optionA = it.options.optionA,
                            optionB = it.options.optionB,
                            optionC = it.options.optionC,
                            optionD = it.options.optionD,
                            optionE = it.options.optionE,
                        )
                    }
                )
            ).data
        )
    }

    override fun putAnnouncement(
        announcementId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            announcementsApi.putAnnouncement(
                announcementId = announcementId,
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                description = description.toTextRequestBody(),
            ).data
        )
    }

    override fun putReference(
        referenceId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            referencesApi.putReference(
                referenceId = referenceId,
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                title = title.toTextRequestBody(),
                description = description.toTextRequestBodyOrNull(),
            ).data
        )
    }

    override fun putTask(
        taskId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = flow {
        emit(
            tasksApi.putTask(
                taskId = taskId,
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
                title = title.toTextRequestBody(),
                description = description.toTextRequestBodyOrNull(),
                dueDate = dueDate.toEpochSecond().toString().toTextRequestBody(),
            ).data
        )
    }

    override fun putQuiz(
        quizId: String,
        title: String,
        description: String,
        quizType: String,
        duration: Int,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<String> = flow {
        val editQuizRequest = EditQuizRequest(
            name = title,
            description = description.ifEmpty { null },
            quizType = quizType,
            duration= duration,
            startDate = startDate.toEpochSecond(),
            endDate = endDate.toEpochSecond()
        )
        emit(
            quizzesApi.putQuiz(
                quizId = quizId,
                editQuizRequest = editQuizRequest,
            ).data.id
        )
    }

    private fun LocalDateTime.toEpochSecond(): Long =
        this.atZone(ZoneId.systemDefault()).toEpochSecond()
}