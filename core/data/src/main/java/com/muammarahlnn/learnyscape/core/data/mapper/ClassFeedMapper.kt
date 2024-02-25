package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.data.util.formatIsoDate
import com.muammarahlnn.learnyscape.core.model.data.ClassDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.model.data.DayModel
import com.muammarahlnn.learnyscape.core.network.model.response.ClassDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ClassFeedResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedMapper, 27/01/2024 12.26
 */

fun List<ClassFeedResponse>.toClassFeedModels() = map {
    it.toClassFeedModel()
}

fun ClassFeedResponse.toClassFeedModel() = ClassFeedModel(
    id = id,
    name = name.orEmpty(),
    createdAt = formatIsoDate(createdAt),
    updatedAt = formatIsoDate(updatedAt),
    type = when (type) {
        ClassFeedResponse.FeedType.ANNOUNCEMENT -> ClassFeedModel.FeedType.ANNOUNCEMENT
        ClassFeedResponse.FeedType.REFERENCE -> ClassFeedModel.FeedType.MODULE
        ClassFeedResponse.FeedType.TASK -> ClassFeedModel.FeedType.ASSIGNMENT
        ClassFeedResponse.FeedType.QUIZ -> ClassFeedModel.FeedType.QUIZ
    },
    uri = uri,
    description = description.orEmpty(),
    profilePicUrl = profilePicUrl.orEmpty(),
)

fun ClassDetailsResponse.toClassDetailsModel() = ClassDetailsModel(
    id = id,
    name = name,
    day = DayModel.getDayModel(day.name),
    startTime = time.toLocalTime(),
    endTime = endTime.toLocalTime(),
    lecturers = lecturers.map { it.toClassMemberModel() },
)