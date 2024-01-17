package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.AnnouncementOverviewModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceOverviewRepository, 17/01/2024 15.08
 */
interface ResourceOverviewRepository {

    fun getAnnouncements(classId: String): Flow<List<AnnouncementOverviewModel>>
}