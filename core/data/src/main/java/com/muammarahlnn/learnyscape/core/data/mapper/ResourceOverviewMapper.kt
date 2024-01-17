package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.AnnouncementOverviewModel
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse

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