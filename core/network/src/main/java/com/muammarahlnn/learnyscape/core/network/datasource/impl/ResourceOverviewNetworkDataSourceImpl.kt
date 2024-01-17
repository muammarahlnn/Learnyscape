package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceOverviewNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceOverviewNetworkDataSourceImpl, 17/01/2024 15.06
 */
@Singleton
class ResourceOverviewNetworkDataSourceImpl @Inject constructor(
    private val announcementsApi: AnnouncementsApi,
) : ResourceOverviewNetworkDataSource {

    override fun getAnnouncements(classId: String): Flow<List<AnnouncementOverviewResponse>> = flow {
        emit(announcementsApi.getAnnouncements(classId).data)
    }
}