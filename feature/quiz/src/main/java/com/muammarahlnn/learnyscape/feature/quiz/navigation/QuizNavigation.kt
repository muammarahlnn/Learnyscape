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
const val QUIZ_ROUTE = "quiz_route"

fun NavController.navigateToQuiz(navOptions: NavOptions? = null) {
    this.navigate(QUIZ_ROUTE, navOptions)
}

fun NavGraphBuilder.quizScreen(
    onQuizClick: (Int) -> Unit,
) {
    composable(route = QUIZ_ROUTE) {
        QuizRoute(
            onQuizClick = onQuizClick,
        )
    }
}