package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementNetworkDataSource, 17/01/2024 15.05
 */
interface ResourceOverviewNetworkDataSource {

    fun getAnnouncements(classId: String): Flow<List<AnnouncementOverviewResponse>>
}