package com.muammarahlnn.learnyscape.core.testing.repository

import com.muammarahlnn.learnyscape.core.data.repository.ResourceCreateRepository
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.annotations.TestOnly
import java.io.File
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TestResourceCreateRepository, 26/05/2024 23.59
 */
class TestResourceCreateRepository : ResourceCreateRepository {

    private val createAnnouncementResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val createModuleResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val createAssignmentResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val createQuizResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val addQuizQuestionsResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val editAnnouncementResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val editModuleResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val editAssignmentResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val editQuizResponseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun createAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = createAnnouncementResponseFlow

    override fun createModule(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = createModuleResponseFlow

    override fun createAssignment(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = createAssignmentResponseFlow

    override fun createQuiz(
        classId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int
    ): Flow<String> = createQuizResponseFlow

    override fun addQuizQuestions(
        quizId: String,
        questions: List<MultipleChoiceQuestionModel>
    ): Flow<String> = addQuizQuestionsResponseFlow

    override fun editAnnouncement(
        announcementId: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = editAnnouncementResponseFlow

    override fun editModule(
        moduleId: String,
        title: String,
        description: String,
        attachments: List<File>
    ): Flow<String> = editModuleResponseFlow

    override fun editAssignment(
        assignmentId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>
    ): Flow<String> = editAssignmentResponseFlow

    override fun editQuiz(
        quizId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int
    ): Flow<String> = editQuizResponseFlow


    @TestOnly
    fun quizCreated(createdQuizId: String) {
        createQuizResponseFlow.tryEmit(createdQuizId)
    }

    @TestOnly
    fun quizQuestionsAdded(response: String) {
        addQuizQuestionsResponseFlow.tryEmit(response)
    }
}