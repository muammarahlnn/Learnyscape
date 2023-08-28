package com.muammarahlnn.learnyscape.feature.quizsession.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionRoute
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionNavigation, 25/08/2023 17.43 by Muammar Ahlan Abimanyu
 */

private const val QUIZ_SESSION_ROUTE = "quiz_session_route"
private const val QUIZ_NAME_ARG = "quiz_name"
private const val QUIZ_DURATION_ARG = "quiz_duration"
const val QUIZ_SESSION_ROUTE_WITH_ARGS =
        "$QUIZ_SESSION_ROUTE/{$QUIZ_NAME_ARG}/{$QUIZ_DURATION_ARG}"

private val urlCharacterEncoding = UTF_8.name()

internal class QuizSessionArgs(
    val quizName: String,
    val quizDuration: Int,
) {

    constructor(
        savedStateHandle: SavedStateHandle,
    ) : this(
        quizName = URLDecoder.decode(
            checkNotNull(savedStateHandle[QUIZ_NAME_ARG]),
            urlCharacterEncoding,
        ),
        quizDuration = checkNotNull(savedStateHandle[QUIZ_DURATION_ARG]),
    )
}

fun NavHostController.navigateToQuizSession(
    quizName: String,
    quizDuration: Int,
) {
    val encodedQuizName = URLEncoder.encode(quizName, urlCharacterEncoding)
    this.navigate("$QUIZ_SESSION_ROUTE/$encodedQuizName/$quizDuration") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.quizSessionScreen() {
    composable(
        route = QUIZ_SESSION_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(QUIZ_NAME_ARG) {
                type = NavType.StringType
            },
            navArgument(QUIZ_DURATION_ARG) {
                type = NavType.IntType
            },
        )
    ) {
        QuizSessionRoute()
    }
}