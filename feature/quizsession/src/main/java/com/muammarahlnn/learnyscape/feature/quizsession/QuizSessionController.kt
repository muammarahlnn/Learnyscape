package com.muammarahlnn.learnyscape.feature.quizsession

import com.muammarahlnn.learnyscape.core.common.contract.NavigationProvider
import com.muammarahlnn.learnyscape.core.common.contract.navigation
import kotlinx.coroutines.CoroutineScope

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionController, 18/02/2024 12.59
 */
class QuizSessionController(
    scope: CoroutineScope,
    val navigateBack: () -> Unit,
) : NavigationProvider<QuizSessionNavigation> by navigation(scope)

sealed interface QuizSessionNavigation {

    data object NavigateBack : QuizSessionNavigation
}