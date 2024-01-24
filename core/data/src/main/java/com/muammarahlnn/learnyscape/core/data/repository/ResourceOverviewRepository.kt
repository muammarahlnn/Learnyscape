package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.AnnouncementOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.AssignmentOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleOverviewModel
import com.muammarahlnn.learnyscape.core.model.data.QuizOverviewModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceOverviewRepository, 17/01/2024 15.08
 */
interface ResourceOverviewRepository {

    fun getAnnouncements(classId: String): Flow<List<AnnouncementOverviewModel>>

    fun getModules(classId: String): Flow<List<ModuleOverviewModel>>

    fun getAssignments(classId: String): Flow<List<AssignmentOverviewModel>>

    fun getQuizzes(classId: String): Flow<List<QuizOverviewModel>>
}