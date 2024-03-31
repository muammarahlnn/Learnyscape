package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.data.util.formatEpochSeconds
import com.muammarahlnn.learnyscape.core.data.util.formatIsoDate
import com.muammarahlnn.learnyscape.core.model.data.AnnouncementDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.QuizDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.model.data.StudentSubmissionModel
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.LecturerTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.StudentTaskSubmissionResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsMapper, 18/01/2024 17.45
 */

fun AnnouncementDetailsResponse.toAnnouncementDetailsModel(attachments: List<File>) = AnnouncementDetailsModel(
    id = id,
    authorName = authorName,
    updatedAt = formatIsoDate(updatedAt),
    description = description.orEmpty(),
    attachments = attachments,
)

fun ReferenceDetailsResponse.toModuleDetailsModel(attachments: List<File>) = ModuleDetailsModel(
    id = id,
    name = name,
    description = description.orEmpty(),
    updatedAt = formatIsoDate(updatedAt),
    attachments = attachments,
)

fun TaskDetailsResponse.toAssignmentDetailsModel(attachments: List<File>) = AssignmentDetailsModel(
    id = id,
    name = name,
    description = description.orEmpty(),
    updatedAt = formatIsoDate(updatedAt),
    dueDate = formatEpochSeconds(dueDate),
    attachments = attachments,
)

fun QuizDetailsResponse.toQuizDetailsModel() = QuizDetailsModel(
    id = id,
    name = name,
    updatedAt = formatIsoDate(updatedAt),
    description = description.orEmpty(),
    startDate = formatEpochSeconds(startDate),
    endDate = formatEpochSeconds(endDate),
    duration = duration,
    quizType = when (type) {
        QuizDetailsResponse.QuizType.MULTIPLE_CHOICE -> QuizType.MULTIPLE_CHOICE
        QuizDetailsResponse.QuizType.PHOTO_ANSWER -> QuizType.PHOTO_ANSWER
    }
)

fun List<LecturerTaskSubmissionResponse>.toAssignmentSubmissionModels() = map {
    it.toStudentSubmission()
}

fun LecturerTaskSubmissionResponse.toStudentSubmission() = StudentSubmissionModel(
    id = id.orEmpty(),
    userId = userId,
    studentName = studentName,
    turnInStatus = turnInStatus,
    turnedInAt = formatIsoDate(turnedInAt),
)

fun List<QuizSubmissionResponse>.toQuizSubmissionModels() = map {
    it.toStudentSubmission()
}

fun QuizSubmissionResponse.toStudentSubmission() = StudentSubmissionModel(
    id = "",
    userId = userId,
    studentName = studentName,
    turnInStatus = turnInStatus,
    turnedInAt = formatIsoDate(turnedInAt),
)

fun StudentTaskSubmissionResponse.toAssignmentSubmissionModel(attachments: List<File>) =
    AssignmentSubmissionModel(
        assignmentSubmissionId = taskSubmissionId,
        userId = userId,
        studentName = studentName,
        turnInStatus = turnInStatus,
        turnedInAt = turnedInAt?.let { formatIsoDate(it) } ?: "",
        attachments = attachments,
    )