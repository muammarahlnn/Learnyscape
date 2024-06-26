package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.api.QuizzesApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.api.constant.ResourceClassPartKey
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.request.TurnInTaskSubmissionRequest
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LecturerTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.StudentTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
import com.muammarahlnn.learnyscape.core.network.util.toFileParts
import com.muammarahlnn.learnyscape.core.network.util.toTextRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsNetworkDataSourceImpl, 18/01/2024 17.38
 */
@Singleton
class ResourceDetailsNetworkDataSourceImpl @Inject constructor(
    private val announcementsApi: AnnouncementsApi,
    private val referencesApi: ReferencesApi,
    private val tasksApi: TasksApi,
    private val quizzesApi: QuizzesApi,
) : ResourceDetailsNetworkDataSource {

    override fun getAnnouncementDetails(announcementId: String): Flow<AnnouncementDetailsResponse> = flow {
        emit(announcementsApi.getAnnouncementDetails(announcementId).data)
    }

    override fun deleteAnnouncement(announcementId: String): Flow<String> = flow {
        emit(announcementsApi.deleteAnnouncement(announcementId).data)
    }

    override fun getReferenceDetails(referenceId: String): Flow<ReferenceDetailsResponse> = flow {
        emit(referencesApi.getReferenceDetails(referenceId).data)
    }

    override fun deleteReference(referenceId: String): Flow<String> = flow {
        emit(referencesApi.deleteReference(referenceId).data)
    }

    override fun getTaskDetails(taskId: String): Flow<TaskDetailsResponse> = flow {
        emit(tasksApi.getTaskDetails(taskId).data)
    }

    override fun lecturerGetTaskSubmissions(taskId: String): Flow<List<LecturerTaskSubmissionResponse>> = flow {
        emit(tasksApi.lecturerGetTaskSubmissions(taskId).data)
    }

    override fun studentGetTaskSubmission(taskId: String): Flow<StudentTaskSubmissionResponse> = flow {
        emit(tasksApi.studentGetTaskSubmission(taskId).data)
    }

    override fun deleteTask(taskId: String): Flow<String> = flow {
        emit(tasksApi.deleteTask(taskId).data)
    }

    override fun getQuizDetails(quizId: String): Flow<QuizDetailsResponse> = flow {
        emit(quizzesApi.getQuizDetails(quizId).data)
    }

    override fun getQuizSubmissions(quizId: String): Flow<List<QuizSubmissionResponse>> = flow {
        emit(quizzesApi.getQuizSubmissions(quizId).data)
    }

    override fun uploadTaskSubmission(taskId: String, attachments: List<File>): Flow<String> = flow {
        emit(
            tasksApi.uploadTaskSubmission(
                taskId = taskId.toTextRequestBody(),
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
            ).data
        )
    }

    override fun updateTaskSubmission(submissionId: String, attachments: List<File>): Flow<String> = flow {
        emit(
            tasksApi.updateTaskSubmission(
                submissionId = submissionId,
                files = attachments.toFileParts(ResourceClassPartKey.FILES_PART),
            ).data
        )
    }

    override fun turnInTaskSubmission(submissionId: String, turnIn: Boolean): Flow<String> = flow {
        emit(
            tasksApi.turnInTaskSubmission(
                submissionId = submissionId,
                turnInTaskSubmissionRequest = TurnInTaskSubmissionRequest(turnedIn = turnIn)
            ).data
        )
    }

    override fun isQuizTaken(quizId: String): Flow<Boolean> = flow {
        emit(
            quizzesApi.getQuizSolutions(quizId).data.problems[0].solution != null
        )
    }

    override fun deleteQuiz(quizId: String): Flow<String> = flow {
        emit(quizzesApi.deleteQuiz(quizId).data)
    }
}