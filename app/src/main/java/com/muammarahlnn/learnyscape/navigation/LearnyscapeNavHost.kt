package com.muammarahlnn.learnyscape.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscape.feature.camera.navigation.cameraScreen
import com.muammarahlnn.learnyscape.feature.camera.navigation.navigateToCamera
import com.muammarahlnn.learnyscape.feature.changepassword.navigation.changePasswordScreen
import com.muammarahlnn.learnyscape.feature.changepassword.navigation.navigateToChangePassword
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.classNavigator
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.navigateToClassNavigator
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HOME_NAVIGATOR_ROUTE
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.homeNavigator
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.joinRequestScreen
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.navigateToJoinRequest
import com.muammarahlnn.learnyscape.feature.notifications.navigation.navigateToNotifications
import com.muammarahlnn.learnyscape.feature.notifications.navigation.notificationsScreen
import com.muammarahlnn.learnyscape.feature.pendingrequest.navigation.navigateToPendingRequest
import com.muammarahlnn.learnyscape.feature.pendingrequest.navigation.pendingRequestScreen
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.navigateToQuizSession
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.quizSessionScreen
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.navigateToResourceCreate
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.resourceCreateScreen
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
    startDestination: String = HOME_NAVIGATOR_ROUTE,
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
            onPendingClassRequestClick = {
                navController.navigateToPendingRequest()
            },
            onChangePasswordButtonClick = navController::navigateToChangePassword
        )
        classNavigator(
            onBackClick = navController::popBackStack,
            onJoinRequestsClick = navController::navigateToJoinRequest,
            onCreateNewResourceClick = navController::navigateToResourceCreate,
            onResourceClassClick = navigateToResourceDetails(navController),
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
            onCameraClosed = navController::navigateUp
        )
        pendingRequestScreen(
            onBackClick = navController::popBackStack
        )
        changePasswordScreen(
            onBackClick = navController::popBackStack
        )
        joinRequestScreen(
            onBackClick = navController::popBackStack
        )
        resourceCreateScreen(
            onCloseClick = navController::popBackStack,
            onCameraClick = navController::navigateToCamera
        )
    }
}

private fun navigateToResourceDetails(
    navController: NavHostController
): (Int) -> Unit = { resourceTypeOrdinal ->
    navController.navigateToResourceDetails(resourceTypeOrdinal)
}