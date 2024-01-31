package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.QuizDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsRepository, 18/01/2024 17.40
 */
interface ResourceDetailsRepository {

    fun getModuleDetails(moduleId: String): Flow<ModuleDetailsModel>

    fun deleteModule(moduleId: String): Flow<String>

    fun getAssignmentDetails(assignmentId: String): Flow<AssignmentDetailsModel>

    fun deleteAssignment(assignmentId: String): Flow<String>

    fun getQuizDetails(quizId: String): Flow<QuizDetailsModel>
}