package com.muammarahlnn.learnyscape.feature.assignmentsubmission

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionController, 20/02/2024 21.21
 */
class AssignmentSubmissionController(
    scope: CoroutineScope,
    val navigateToCamera: () -> Unit,
) : NavigationProvider<AssignmentSubmissionNavigation> by navigation(scope)

sealed interface AssignmentSubmissionNavigation {

    data object NavigateToCamera : AssignmentSubmissionNavigation
}