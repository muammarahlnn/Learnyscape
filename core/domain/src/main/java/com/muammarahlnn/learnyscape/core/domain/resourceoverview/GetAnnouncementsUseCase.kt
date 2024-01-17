package com.muammarahlnn.learnyscape.core.domain.resourceoverview

import com.muammarahlnn.learnyscape.core.model.data.AnnouncementOverviewModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetAnnouncementsUseCase, 17/01/2024 15.21
 */
fun interface GetAnnouncementsUseCase {

    operator fun invoke(classId: String): Flow<List<AnnouncementOverviewModel>>
}