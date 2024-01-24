package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toAnnouncementOverviewModels
import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentOverviewModel
import com.muammarahlnn.learnyscape.core.data.mapper.toModuleOverviewModels
import com.muammarahlnn.learnyscape.core.data.mapper.toQuizOverviewModels
import com.muammarahlnn.learnyscape.core.data.repository.ResourceOverviewRepository
import com.muammarahlnn.learnyscape.core.model.data.AnnouncementOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.QuizOverviewModel
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceOverviewNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceOverviewRepositoryImpl, 17/01/2024 15.10
 */
class ResourceOverviewRepositoryImpl @Inject constructor(
    private val resourceOverviewNetworkDataSource: ResourceOverviewNetworkDataSource,
) : ResourceOverviewRepository {

    override fun getAnnouncements(classId: String): Flow<List<AnnouncementOverviewModel>> =
        resourceOverviewNetworkDataSource.getAnnouncements(classId).map { announcementResponses ->
            announcementResponses.toAnnouncementOverviewModels()
        }

    override fun getModules(classId: String): Flow<List<ModuleOverviewModel>> =
        resourceOverviewNetworkDataSource.getReferences(classId).map { referenceResponses ->
            referenceResponses.toModuleOverviewModels()
        }

    override fun getAssignments(classId: String): Flow<List<AssignmentOverviewModel>> =
        resourceOverviewNetworkDataSource.getTasks(classId).map { taskResponses ->
            taskResponses.toAssignmentOverviewModel()
        }

    override fun getQuizzes(classId: String): Flow<List<QuizOverviewModel>> =
        resourceOverviewNetworkDataSource.getQuizzes(classId).map { quizResponses ->
            quizResponses.toQuizOverviewModels()
        }
}