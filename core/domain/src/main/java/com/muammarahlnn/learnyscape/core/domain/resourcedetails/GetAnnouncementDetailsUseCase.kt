package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import com.muammarahlnn.learnyscape.core.model.data.AnnouncementDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetAnnouncementDetailsUseCase, 31/01/2024 16.15
 */
fun interface GetAnnouncementDetailsUseCase {

    operator fun invoke(announcementId: String): Flow<AnnouncementDetailsModel>
}