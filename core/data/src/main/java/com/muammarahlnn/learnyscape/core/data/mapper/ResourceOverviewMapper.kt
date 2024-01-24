package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.data.util.formatEpochSeconds
import com.muammarahlnn.learnyscape.core.data.util.formatIsoDate
import com.muammarahlnn.learnyscape.core.model.data.AnnouncementOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.QuizOverviewModel
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskOverviewResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementMapper, 17/01/2024 15.10
 */

fun List<AnnouncementOverviewResponse>.toAnnouncementOverviewModels() = map {
    it.toAnnouncementOverviewModel()
}

fun AnnouncementOverviewResponse.toAnnouncementOverviewModel() = AnnouncementOverviewModel(
    id = id,
    updatedAt = updatedAt,
    authorName = authorName,
)

fun List<ReferenceOverviewResponse>.toModuleOverviewModels() = map {
    it.toModuleOverviewModel()
}

fun ReferenceOverviewResponse.toModuleOverviewModel() = ModuleOverviewModel(
    id = id,
    name = name,
    updatedAt = formatIsoDate(updatedAt),
)

fun List<TaskOverviewResponse>.toAssignmentOverviewModel() = map {
    it.toAssignmentOverviewModel()
}

fun TaskOverviewResponse.toAssignmentOverviewModel() = AssignmentOverviewModel(
    id = id,
    name = name,
    updatedAt = formatIsoDate(updatedAt),
    dueDate = formatEpochSeconds(dueDate),
)

fun List<QuizOverviewResponse>.toQuizOverviewModels() = map {
    it.toQuizOverviewModel()
}

fun QuizOverviewResponse.toQuizOverviewModel() = QuizOverviewModel(
    id = id,
    name = name,
    startDate = formatEpochSeconds(startDate)
)