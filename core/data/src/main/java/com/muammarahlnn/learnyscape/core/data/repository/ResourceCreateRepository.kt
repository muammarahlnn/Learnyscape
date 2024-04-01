package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateRepository, 13/01/2024 05.31
 */
interface ResourceCreateRepository {

    fun createAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun createModule(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun createAssignment(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>,
    ): Flow<String>

    fun createQuiz(
        classId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int,
    ): Flow<String>

    fun addQuizQuestions(
        quizId: String,
        questions: List<MultipleChoiceQuestionModel>,
    ): Flow<String>

    fun editAnnouncement(
        announcementId: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun editModule(
        moduleId: String,
        title: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun editAssignment(
        assignmentId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>,
    ): Flow<String>

    fun editQuiz(
        quizId: String,
        title: String,
        description: String,
        quizType: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int,
    ): Flow<String>
}