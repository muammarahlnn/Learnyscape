package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateNetworkDataSource, 13/01/2024 04.35
 */
interface ResourceCreateNetworkDataSource {

    fun postAnnouncement(
        classId: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun postReference(
        classId: String,
        title: String,
        description: String,
        attachments: List<File>,
    ): Flow<String>

    fun postTask(
        classId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
        attachments: List<File>,
    ): Flow<String>

    fun postQuiz(
        classId: String,
        title: String,
        description: String,
        quizType: String,
        duration: Int,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Flow<String>

    fun postQuizProblems(
        quizId: String,
        multipleChoiceQuestions: List<MultipleChoiceQuestionModel>,
    ): Flow<String>
}