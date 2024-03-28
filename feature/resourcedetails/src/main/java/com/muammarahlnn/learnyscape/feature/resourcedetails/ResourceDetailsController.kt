package com.muammarahlnn.learnyscape.feature.resourcedetails

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateController, 18/02/2024 19.48
 */
class ResourceDetailsController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToCamera: () -> Unit,
    val navigateToQuizSession: (String, Int, String, Int) -> Unit,
    val navigateToSubmissionDetails: (Int,String, String, String) -> Unit,
    val navigateToResourceCreate: (String, Int, String) -> Unit,
) : NavigationProvider<ResourceDetailsNavigation> by navigation(scope)

sealed interface ResourceDetailsNavigation {

    data object NavigateBack : ResourceDetailsNavigation

    data object NavigateToCamera : ResourceDetailsNavigation

    data class NavigateToQuizSession(
        val quizId: String,
        val quizTypeOrdinal: Int,
        val quizName: String,
        val quizDuration: Int,
    ) : ResourceDetailsNavigation

    data class NavigateToSubmissionDetails(
        val submissionTypeOrdinal: Int,
        val submissionId: String,
        val studentId: String,
        val studentName: String,
    ) : ResourceDetailsNavigation

    data class NavigateToResourceCreate(
        val classId: String,
        val resourceTypeOrdinal: Int,
        val resourceId: String,
    ) : ResourceDetailsNavigation
}