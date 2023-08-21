package com.muammarahlnn.learnyscape.feature.quiz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.quiz.QuizRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizNavigation, 03/08/2023 15.55 by Muammar Ahlan Abimanyu
 */
const val quizRoute = "quiz_route"

fun NavController.navigateToQuiz(navOptions: NavOptions? = null) {
    this.navigate(quizRoute, navOptions)
}

fun NavGraphBuilder.quizScreen(
    onQuizClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = quizRoute) {
        QuizRoute(
            onQuizClick = onQuizClick,
            onBackClick = onBackClick,
        )
    }
}