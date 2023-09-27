package com.muammarahlnn.learnyscape.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.muammarahlnn.learnyscape.feature.camera.navigation.cameraScreen
import com.muammarahlnn.learnyscape.feature.camera.navigation.navigateToCamera
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.classNavigator
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.navigateToClassNavigator
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HOME_NAVIGATOR_ROUTE
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.homeNavigator
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.navigateToHomeNavigator
import com.muammarahlnn.learnyscape.feature.login.navigation.LOGIN_ROUTE
import com.muammarahlnn.learnyscape.feature.login.navigation.loginScreen
import com.muammarahlnn.learnyscape.feature.login.navigation.navigateToLogin
import com.muammarahlnn.learnyscape.feature.notifications.navigation.navigateToNotifications
import com.muammarahlnn.learnyscape.feature.notifications.navigation.notificationsScreen
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.navigateToQuizSession
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.quizSessionScreen
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.navigateToResourceDetails
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.resourceDetailsScreen
import com.muammarahlnn.learnyscape.ui.LearnyscapeAppState


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NeoLearnyscapeNavHost, 14/09/2023 21.33 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeNavHost(
    appState: LearnyscapeAppState,
    modifier: Modifier = Modifier,
    startDestination: String = LOGIN_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(600)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(600)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(600)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(600)
            )
        }
    ) {
        loginScreen(
            onLoginButtonClick = {
                navController.navigateToHomeNavigator(
                    navOptions = navOptions {
                        popUpTo(LOGIN_ROUTE) {
                            inclusive = true
                        }
                    }
                )
            }
        )
        homeNavigator(
            onNotificationsClick = {
                navController.navigateToNotifications()
            },
            onClassClick = {
                navController.navigateToClassNavigator()
            },
            onCameraActionClick = {
                navController.navigateToCamera()
            },
            onLogoutButtonClick = {
                navController.navigateToLogin(
                    navOptions = navOptions {
                        popUpTo(HOME_NAVIGATOR_ROUTE) {
                            inclusive = true
                        }
                    }
                )
            }
        )
        classNavigator(
            onResourceClassClick = navigateToResourceDetails(navController),
            onBackClick = navController::popBackStack
        )
        notificationsScreen(
            onNotificationClick = navigateToResourceDetails(navController),
            onBackClick = navController::popBackStack
        )
        resourceDetailsScreen(
            onConfirmStartQuizDialog = { quizTypeOrdinal, quizName, quizDuration ->
                navController.navigateToQuizSession(
                    quizTypeOrdinal,
                    quizName,
                    quizDuration
                )
            },
            onCameraActionClick = {
                navController.navigateToCamera()
            },
            onBackClick = navController::popBackStack
        )
        quizSessionScreen(
            onQuizIsOver = navController::popBackStack
        )
        cameraScreen(
            onCameraPermissionDenied = navController::popBackStack
        )
    }
}

private fun navigateToResourceDetails(
    navController: NavHostController
): (Int) -> Unit = { resourceTypeOrdinal ->
    navController.navigateToResourceDetails(resourceTypeOrdinal)
}