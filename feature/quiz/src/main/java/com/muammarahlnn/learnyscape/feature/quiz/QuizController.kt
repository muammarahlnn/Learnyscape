package com.muammarahlnn.learnyscape.feature.quiz

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizController, 18/02/2024 01.34
 */
class QuizController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
    val navigateToResourceDetails: (String, String, Int) -> Unit,
    val navigateToResourceCreate: (String, Int) -> Unit,
) : NavigationProvider<QuizNavigation> by navigation(scope)

sealed interface QuizNavigation {

    data object NavigateBack : QuizNavigation

    data class NavigateToResourceDetails(
        val classId: String,
        val resourceId: String,
        val resourceTypeOrdinal: Int,
    ) : QuizNavigation

    data class NavigateToResourceCreate(
        val classId: String,
        val resourceTypeOrdinal: Int,
    ) : QuizNavigation
}