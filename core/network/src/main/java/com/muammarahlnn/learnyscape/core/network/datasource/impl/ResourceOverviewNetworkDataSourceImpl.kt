package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.api.QuizzesApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceOverviewNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.AnnouncementOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.QuizOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceOverviewResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskOverviewResponse
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
    private val referencesApi: ReferencesApi,
    private val tasksApi: TasksApi,
    private val quizzesApi: QuizzesApi,
) : ResourceOverviewNetworkDataSource {

    override fun getAnnouncements(classId: String): Flow<List<AnnouncementOverviewResponse>> = flow {
        emit(announcementsApi.getAnnouncements(classId).data)
    }

    override fun getReferences(classId: String): Flow<List<ReferenceOverviewResponse>> = flow {
        emit(referencesApi.getReferences(classId).data)
    }

    override fun getTasks(classId: String): Flow<List<TaskOverviewResponse>> = flow {
        emit(tasksApi.getTasks(classId).data)
    }

    override fun getQuizzes(classId: String): Flow<List<QuizOverviewResponse>> = flow {
        emit(quizzesApi.getQuizzes(classId).data)
    }
}