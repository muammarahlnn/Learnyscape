package com.muammarahlnn.submissiondetails

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsController, 18/02/2024 21.34
 */
class SubmissionDetailsController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
) : NavigationProvider<SubmissionDetailsNavigation> by navigation(scope)

sealed interface SubmissionDetailsNavigation {

    data object NavigateBack : SubmissionDetailsNavigation
}