package com.muammarahlnn.learnyscape.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.muammarahlnn.learnyscape.feature.camera.navigation.cameraScreen
import com.muammarahlnn.learnyscape.feature.camera.navigation.navigateToCamera
import com.muammarahlnn.learnyscape.feature.changepassword.navigation.changePasswordScreen
import com.muammarahlnn.learnyscape.feature.changepassword.navigation.navigateToChangePassword
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.classNavigator
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.navigateToClassNavigator
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.HOME_NAVIGATOR_ROUTE
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.homeNavigator
import com.muammarahlnn.learnyscape.feature.homenavigator.navigation.navigateToHomeNavigator
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.joinRequestScreen
import com.muammarahlnn.learnyscape.feature.joinrequest.navigation.navigateToJoinRequest
import com.muammarahlnn.learnyscape.feature.login.navigation.LOGIN_ROUTE
import com.muammarahlnn.learnyscape.feature.login.navigation.loginScreen
import com.muammarahlnn.learnyscape.feature.login.navigation.navigateToLogin
import com.muammarahlnn.learnyscape.feature.notifications.navigation.navigateToNotifications
import com.muammarahlnn.learnyscape.feature.notifications.navigation.notificationsScreen
import com.muammarahlnn.learnyscape.feature.pendingrequest.navigation.navigateToPendingRequest
import com.muammarahlnn.learnyscape.feature.pendingrequest.navigation.pendingRequestScreen
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.navigateToQuizSession
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.quizSessionScreen
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.navigateToResourceCreate
import com.muammarahlnn.learnyscape.feature.resourcecreate.navigation.resourceCreateScreen
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.navigateToResourceDetails
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.resourceDetailScreen
import com.muammarahlnn.learnyscape.ui.LearnyscapeAppState
import com.muammarahlnn.submissiondetails.navigation.navigateToSubmissionDetails
import com.muammarahlnn.submissiondetails.navigation.submissionDetailsScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NeoLearnyscapeNavHost, 14/09/2023 21.33 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeNavHost(
    appState: LearnyscapeAppState,
    startDestination: String,
    modifier: Modifier = Modifier,
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
            navigateToHomeNavigator = {
                navController.navigateToHomeNavigator(popUpToRoute = LOGIN_ROUTE)
            }
        )
        homeNavigator(
            navigateToNotifications = navController::navigateToNotifications,
            navigateToClass = navController::navigateToClassNavigator,
            navigateToCamera = navController::navigateToCamera,
            navigateToPendingRequestClass = navController::navigateToPendingRequest,
            navigateToChangePassword = navController::navigateToChangePassword,
            navigateToLogin = {
                navController.navigateToLogin(popUpToRoute = HOME_NAVIGATOR_ROUTE)
            }
        )
        classNavigator(
            navigateBack = navController::popBackStack,
            navigateToJoinRequests = navController::navigateToJoinRequest,
            navigateToResourceCreate = navController::navigateToResourceCreate,
            navigateToResourceDetails = navController::navigateToResourceDetails,
        )
        notificationsScreen(
            navigateBack = navController::popBackStack,
            navigateToResourceDetails = navController::navigateToResourceDetails,
        )
        resourceDetailScreen(
            navigateBack = navController::popBackStack,
            navigateToCamera = navController::navigateToCamera,
            navigateToQuizSession = navController::navigateToQuizSession,
            navigateToSubmissionDetails = navController::navigateToSubmissionDetails,
            navigateToResourceCreate = navController::navigateToResourceCreate
        )
        quizSessionScreen(
            navigateBack = navController::popBackStack
        )
        cameraScreen(
            onCameraClosed = navController::navigateUp
        )
        pendingRequestScreen(
            navigateBack = navController::popBackStack
        )
        changePasswordScreen(
            navigateBack = navController::popBackStack
        )
        joinRequestScreen(
            navigateBack = navController::popBackStack,
        )
        resourceCreateScreen(
            navigateBack = navController::popBackStack,
            navigateToCamera = navController::navigateToCamera
        )
        submissionDetailsScreen(
            navigateBack = navController::popBackStack
        )
    }
}