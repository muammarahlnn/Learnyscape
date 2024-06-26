package com.muammarahlnn.learnyscape.feature.quizsession.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muammarahlnn.learnyscape.core.common.decoder.StringCodec
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionController
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionNavigation, 25/08/2023 17.43 by Muammar Ahlan Abimanyu
 */

private const val QUIZ_SESSION_ROUTE = "quiz_session_route"
private const val QUIZ_ID_ARG = "quiz_id_arg"
private const val QUIZ_TYPE_ORDINAL_ARG = "quiz_type_ordinal"
private const val QUIZ_NAME_ARG = "quiz_name"
private const val QUIZ_DURATION_ARG = "quiz_duration"
const val QUIZ_SESSION_ROUTE_WITH_ARGS =
        "$QUIZ_SESSION_ROUTE/{$QUIZ_ID_ARG}/{$QUIZ_TYPE_ORDINAL_ARG}/{$QUIZ_NAME_ARG}/{$QUIZ_DURATION_ARG}"

internal class QuizSessionArgs(
    val quizId: String,
    val quizTypeOrdinal: Int,
    val quizName: String,
    val quizDuration: Int,
) {

    constructor(
        savedStateHandle: SavedStateHandle,
    ) : this(
        quizId = StringCodec.decode(checkNotNull(savedStateHandle[QUIZ_ID_ARG])),
        quizTypeOrdinal = checkNotNull(savedStateHandle[QUIZ_TYPE_ORDINAL_ARG]),
        quizName = StringCodec.decode(checkNotNull(savedStateHandle[QUIZ_NAME_ARG])),
        quizDuration = checkNotNull(savedStateHandle[QUIZ_DURATION_ARG]),
    )
}

fun NavHostController.navigateToQuizSession(
    quizId: String,
    quizTypeOrdinal: Int,
    quizName: String,
    quizDuration: Int,
) {
    val encodedQuizId = StringCodec.encode(quizId)
    val encodedQuizName = StringCodec.encode(quizName)
    this.navigate("$QUIZ_SESSION_ROUTE/$encodedQuizId/$quizTypeOrdinal/$encodedQuizName/$quizDuration") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.quizSessionScreen(
    navigateBack: () -> Unit,
) {
    composable(
        route = QUIZ_SESSION_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(QUIZ_ID_ARG) {
                type = NavType.StringType
            },
            navArgument(QUIZ_TYPE_ORDINAL_ARG) {
                type = NavType.IntType
            },
            navArgument(QUIZ_NAME_ARG) {
                type = NavType.StringType
            },
            navArgument(QUIZ_DURATION_ARG) {
                type = NavType.IntType
            },
        )
    ) {
        QuizSessionRoute(
            controller = QuizSessionController(
                scope = rememberCoroutineScope(),
                navigateBack = navigateBack,
            )
        )
    }
}